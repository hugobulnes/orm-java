package com.hugobulnes.jdb.query;

import com.hugobulnes.jdb.Model;
import com.hugobulnes.jdb.DatabaseSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertQuery<T> extends EntityList<T> implements Query<T>{

    private Model<T> model;
    //private ArrayList<T> entities;

    public InsertQuery(Class<T> model){
        try{
            this.model = new Model(model);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get the model entity from the query
     * @return Model<T>
     */ 
    public Model<T> getModel() throws Exception{
        return this.model;
    }

    /**
     * Execute a statement and returns a response from the server
     * @param connection Connection
     * @return boolean
     */
    public boolean execute(DatabaseSession session){
        boolean ok = false;
        PreparedStatement stmt = null;
        ResultSet newKeys = null;
        try{
            if(this.size() > 0){

                stmt = session.prepareForInsert(this.compose());
                
                //Set placeholder values for each entity store in the query
                for(T entity: this){

                    Object[] values = this.model.toArray(entity);

                    for(int e = 0; e < values.length; e++){
                        stmt.setObject(e+1, values[e] );
                    }
                    stmt.addBatch();
                }

                int[] success = stmt.executeBatch();

                newKeys = stmt.getGeneratedKeys();

                //Add othe keys to the entities references
                int i = 0;
                for(T entity : this){
                    ok = false;
                    if(success[i++] > 0){
                        newKeys.next();                
                        this.model.setKey(entity, newKeys.getInt(1));
                        ok = true;
                    }
                }

            }else{
               throw new Error("There are no entities to process");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(newKeys != null){
                    newKeys.close();
                }
                if(stmt != null){
                    stmt.close();
                }
            }catch(Exception se){ se.printStackTrace(); }
            return ok;
        }
    }

    /**
     * Generate a query string incluiding filters and other part of the 
     * statement
     * @param operation String - a operation mode for the query
     */ 
    public String compose() throws Exception{

		String query = "INSERT into " + this.model.getTable() + " (";
        String placeHolders = "";
        String[] columnNames = this.model.columnsToArray();

		//Add columns to query
        for(int i=0; i < columnNames.length; i++) {
            query += columnNames[i] + "";
            placeHolders += "?";
            if(i != columnNames.length-1) {
                query += ", ";
                placeHolders += ", ";
            }					
        }

		//Add Values
		query += ") VALUES (" + placeHolders + ")";

        return query;
    }     

}

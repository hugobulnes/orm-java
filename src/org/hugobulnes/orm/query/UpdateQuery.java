package org.hugobulnes.orm.query;

import org.hugobulnes.orm.Model;
import org.hugobulnes.orm.DatabaseSession;
import java.util.Iterator;
import java.sql.PreparedStatement;

public class UpdateQuery<T> extends EntityList<T> implements Query<T>{

    private Model<T> model;
    private Iterator<T> entityIterator;

    public UpdateQuery(Class<T> model) throws Exception{
        this.model = new Model(model);
    }

    /**
     * Get the model entity from the query
     * @return Model<T>
     */ 
    public Model<T> getModel() throws Exception{
        return this.model;
    }

    /**
     * Prepare a query statement with all possible values placed
     * @param DatabaseSession session
     * @return boolean
     */
    public boolean execute(DatabaseSession session) throws Exception{

        boolean ok = false;

        if(this.size() > 0){

            ok = true;

            session.beginTransaction();

            for(T entity: this){

                String query = "UPDATE " + this.model.getTable() + " set ";

                String[] columns = this.model.columnsWithValues(entity);
                for(int i = 0; i < columns.length; i++){
                    query += columns[i] + " = ?";
                    if(i != columns.length-1){
                        query += ", ";
                    }
                }
                query += " where "+ this.model.getKeyColumn() + " = ?";
            
                PreparedStatement stmt = session.prepareStatement(query);

                int i = 0;
                while(i < columns.length){
                    stmt.setObject(
                            i+1, this.model.getValue(entity, columns[i]));
                    i++;
                }
                stmt.setObject(i+1, this.model.getKeyValue(entity));

                stmt.executeUpdate(); 
            }

            session.closeTransaction();

        }

        return ok;
    }

    /**
     * Generate a query string incluiding filters and other part of the 
     * statement
     * @param operation String - a operation mode for the query
     */ 
    public String compose() throws Exception{
        return "";
    }     
}

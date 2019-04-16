package com.hugobulnes.jdb.query;

import com.hugobulnes.jdb.Model;
import com.hugobulnes.jdb.query.Filter;
import com.hugobulnes.jdb.DatabaseSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeleteQuery<T> implements Query<T>{

    private Model<T> model;
    private ArrayList<Filter> filters;
    //private ArrayList<T> entities;

    public DeleteQuery(Class<T> model) throws Exception{
        this.model = new Model(model);
        this.filters = new ArrayList();
    }

    /**
     * Get the model entity from the query
     * @return Model<T>
     */ 
    public Model<T> getModel() throws Exception{
        return this.model;
    }

    /**
     * Add a filter to the query
     * @param Filter<E> 
     */
    public <E> void filterBy(Filter<E> filter) throws Exception{
        this.filters.add(filter);
    } 

    /**
     * Execute a statement and returns a response from the server
     * @param connection Connection
     * @return boolean
     */
    public boolean execute(DatabaseSession session) throws Exception{

        boolean ok = false;

        PreparedStatement stmt = session.prepareForSelect(this.compose());

        for(int i = 0; i < this.filters.size(); i++){
            Object o = this.filters.get(i).getValue();

            if(o == null){
                throw new Exception("Filter does not have a value set");
            }

            stmt.setObject(i+1, o );
        }
        int success = stmt.executeUpdate();

        ok = success > 0 ? true : false;

        stmt.close();

        return ok;
    }

    /**
     * Generate a query string incluiding filters and other part of the 
     * statement
     * @param operation String - a operation mode for the query
     */ 
    public String compose() throws Exception{

		String query = "DELETE from " + this.model.getTable() + " where ";

		//Add filters to query		
		if(this.filters.size() > 0) {
			for(int i=0; i < this.filters.size(); i++) {
				query += this.filters.get(i).compose() + " ";
				if(i != this.filters.size()-1) {
					query += "and ";
				}					
			}
		}

        return query;
    }     

}

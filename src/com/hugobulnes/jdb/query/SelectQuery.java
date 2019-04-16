package com.hugobulnes.jdb.query;

import com.hugobulnes.jdb.Model;
import com.hugobulnes.jdb.DatabaseSession;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SelectQuery<T> extends EntityList<T> implements Query<T>{

    private ArrayList<Filter> filters;
    private Model<T> model;
    private String[] selectColumns;

    public SelectQuery(Class<T> model) throws Exception{
        this.model = new Model(model);
        this.filters = new ArrayList();
    }

    /**
     * select the columns that are going to be retrived from the query
     * @param columns Strings... - the columns from the entity table
     */ 
    public void selectBy(String... columns) throws Exception{
        this.selectColumns = columns.length > 0 ? columns : null;
    }

    /**
     * Add a filter to the query
     * @param Filter<E> 
     */
    public <E> void filterBy(Filter<E> filter) throws Exception{
        this.filters.add(filter);
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
        PreparedStatement stmt = session.prepareForSelect(this.compose());
        
        for(int i = 0; i < this.filters.size(); i++){
            stmt.setObject(i+1, this.filters.get(i).getValue() );
        }

        ResultSet results = stmt.executeQuery();
    
        results.last();
        int last = results.getRow(); 
        results.beforeFirst();

        if(last > 0 ){

            ResultSetMetaData metadata = results.getMetaData();
            int colSize = metadata.getColumnCount();
            results.beforeFirst();

            results.next();
            while(results.isAfterLast() == false){
                T entity = this.model.create();
                        
                for(int i = 1; i <= colSize; i++){
                    String colName = metadata.getColumnName(i);
                
                    Object o = results.getObject(
                            colName, this.model.fieldTypeFromColumn(colName));

                    this.model.setValue(entity, colName, o);
                } 

                this.add(entity);

                results.next();
            }
            ok = true;
        }

        return ok;
    }

    /**
     * Generate a query string incluiding filters and other part of the 
     * statement
     * @param operation String - a operation mode for the query
     */ 
    public String compose() throws Exception{

		String query = "select ";
        
		//Add columns to query
		if(this.selectColumns != null) {						
			for(int i=0; i < this.selectColumns.length; i++) {
				query += this.selectColumns[i] + " ";
				if(i != this.selectColumns.length-1) {
					query += ", ";
				}					
			}
		} else {
			query += "* ";
		}
		
		//Add table to query
		query += "from " + this.model.getTable() + " ";

		//Add filters to query		
		if(this.filters.size() > 0) {
			query += "where ";
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

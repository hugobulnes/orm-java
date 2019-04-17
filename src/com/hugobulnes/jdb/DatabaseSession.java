package com.hugobulnes.jdb;

import com.hugobulnes.jdb.query.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ParameterMetaData;
import java.util.Collection;

public class DatabaseSession {

	private static DatabaseSession openSession = null; 
	private Connection connection = null;

	//Won't use constructor to create connections
    private DatabaseSession(String host, String user, String password) 
        throws SQLException, ClassNotFoundException  {        
        
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(host, user, password);     
           
    }

    /**
     * Use this method to connect to establish a connection to the database 
     * passing the connection parameters. 
     * It will check if open connection exist or it will create a new one
     * @param host String
     * @param user String
     * @param password String
     * @return Connector
     */
    static public DatabaseSession connect(
            String host, String user, String password) {
        try{
            if(DatabaseSession.openSession == null) {
                DatabaseSession.openSession = new DatabaseSession(
                        host, user, password);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return DatabaseSession.openSession;
    }

    /**
     * Use this method to connect to get an open connection to the database.
     * @return Connector
     */
    static public DatabaseSession connect() {
        return DatabaseSession.openSession;
    }
    
    /**
     * This will disconnect the current session connection 
     */
    public void disconnect() throws Exception {
    	if(DatabaseSession.openSession != null) {
    		DatabaseSession.openSession.connection.close();
    		DatabaseSession.openSession.connection = null;
    		DatabaseSession.openSession  = null;
    	}
    }
    
   public void beginTransaction() throws Exception{
        this.connection.setAutoCommit(false);
   }

   public void closeTransaction() throws Exception{
        if(!this.connection.getAutoCommit()){
            this.connection.commit();         
            this.connection.setAutoCommit(true);
        }
   }

   /**
    * Will check if connection is open or not
    * @return boolean
    */ 
   public boolean isClosed() throws Exception{
       return this.connection.isClosed();
   }

   /**
    * Generates a preparedStatement with insert constants
    * @para query String
    * @return PreparedStatement
    */
   public PreparedStatement prepareForInsert(String query) throws Exception{
        PreparedStatement stmt = this.connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        return stmt;
   } 

   /**
    * Generates a preparedStatement with select constants
    * @para query String
    * @return PreparedStatement
    */
   public PreparedStatement prepareForSelect(String query) throws Exception{
        PreparedStatement stmt = this.connection.prepareStatement(query);
        return stmt;
   } 

    public PreparedStatement prepareStatement(String query) throws Exception{
        return this.connection.prepareStatement(query);
    }


   /**
    * execute a query and returns either ResultCollection with empty objects 
    * or with the number of objects retrived from the database. A prepared 
    * statement is used for this and the result set returned from the execution
    * of the query is used.
    * @TODO Maybe declare execute method on query to allow custom manager of 
    * execute 
    * idea
    * @param query Query<T>
    * @return ResultCollection<T>
    */ 
   public <T, B extends Query<T>> Collection<?> execute(B query) 
       throws Exception{
       return null;
   }
    
   @Override
   public void finalize() throws SQLException{
       this.connection.close();
   }
}

import com.hugobulnes.jdb.query.SelectQuery;
import com.hugobulnes.jdb.*;

/**
 * This is a simple example that retrieves all the students from the database
 * using the SelectQuery
 */  
public class Example{
    public static void main(String[] args){
    
        //create a query to retrieve all the students from the database
        SelectQuery<Student> query = new SelectQuery(Student.class);

        //create a session object that will be used to execute the query
        DatabaseSession session = DatabaseSession.connect(
                ConnexionParameters.url,
                ConnexionParameters.user,
                ConnexionParameters.password
                );


        //Execute the query
        query.execute(session);

        //Print the first student retrieved from the database
        System.out.println(query.get(0));

    }
}

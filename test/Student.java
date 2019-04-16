import com.hugobulnes.jdb.annotations.Table;
import com.hugobulnes.jdb.annotations.Column;
import com.hugobulnes.jdb.annotations.Id;
import com.hugobulnes.jdb.Model;


/**
 * This is an example class that that uses specific annotations to map 
 * different aspects of the database. The database will have a table `profile`
 * that will be mapped to this class. For the correct use of the framework,
 * the custom class need to have setters and getter for all the class 
 * variables.
 *
 */
@Table("profile")
public class Student{

    @Id
    @Column("pro_id")
    private int id;
    @Column("pro_userName")
	private String userName;
    @Column("pro_password")
	private String password;   
    @Column("pro_type")
	private int type;   
	@Column("pro_name")
    private String name;
	
	/**
	 * Default constructor
	 */
	public Student(){		
    }

	public Student(String name, String userName, String password, int type){		
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.name = name;
	}

    /**
	 * Get User Profile Id
	 * @return Int
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Set User Profile Id
	 * @param int id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get User Profile Username
	 * @return String
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set User Profile Username
	 * @param String userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get User Profile Password
	 * @return String
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Set User Profile Password
	 * @param String password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get User Profile Type
	 * @return ProfileTypes
	 */
	public int getType( ) {
		return this.type;
	}
	
	/**
	 * Set User Profile Type
	 * @param ProfileTypes
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	
    /**
     * Get Name of User
     * @return String
     */
    public String getName(){
        return this.name;
    }

    /**
     * Set Name for User
     * @param String name
     */
    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return this.id + ", " + this.userName + ", " + this.name + ", " +
            this.password;
    }

}

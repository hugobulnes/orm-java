import com.hugobulnes.jdb.annotations.Table;
import com.hugobulnes.jdb.annotations.Column;
import com.hugobulnes.jdb.annotations.Id;

@Table("student")
public class Student{

    @Id
    @Column("id")
    private int id;
    @Column("userName")
	private String userName;
    @Column("password")
	private String password;   
    @Column("age")
	private int age;   
	
	public Student(){		
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

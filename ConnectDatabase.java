import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

// singleton class: can only have one instance of class at a time
// only a single instance of a class is required to control the action throughout the execution

public class ConnectDatabase 
{
	private static ConnectDatabase instance;
	private Connection con;

	private ConnectDatabase()
	{
		try
		{
			String url = "jdbc:mysql://localhost:3306/memvocdb";
			String username = "root";
			String password = "oop2_ss22";			
			con = DriverManager.getConnection(url, username, password);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return con;
	}
	
	 public static ConnectDatabase getInstance() throws SQLException 
	 {
	        if (instance == null) {
	            instance = new ConnectDatabase();
	        } else if (instance.getConnection().isClosed()) {
	            instance = new ConnectDatabase();
	        }
	        return instance;
	    }
}

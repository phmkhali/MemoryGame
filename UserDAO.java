import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// accesses user database
// methods to perform actions in the database

public class UserDAO 
{
	// add a new account (user) to user database
	public void add(String username, String password) {
		try
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO user(username,password) VALUES (?,?)");
	
			// replace ? value
			ps.setString(1, username); 		
			ps.setString(2, password); 	
			ps.executeUpdate();			
			con.close();
				System.out.println("user added");
			
		} catch (SQLException e) {}		
	}

	// change password from logged in user
	public void updatePassword(String password) {
		try 
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE user SET password = ? WHERE username = ?");
		
				// replace ? value
				ps.setString(1, password); 		
				ps.setString(2, User.getUsername()); 	
				ps.executeUpdate();			
				con.close();
					System.out.println("password changed");
					
		} catch (SQLException ex) {}	
		
	}
	
	// change username from logged in user
	public void updateUsername(String username) {
		try 
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE user SET username = ? WHERE username = ?");
			
			ps.setString(1, username); 
			ps.setString(2, User.getUsername()); 		
			ps.executeUpdate();
			con.close();
				System.out.println("username changed");
	
	} catch (SQLException ex) {}	
		}


	// delete logged in user from database
	public void delete(String username) {
		try 
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE username = ?");
		
				// replace ? value
				ps.setString(1, User.getUsername());
				ps.executeUpdate();
				con.close();
					System.out.println("user deleted");
		} catch (SQLException ex) {}
		
	}
	
	// scan database for entered username
	public boolean checkUsernameTaken(String username) {
		
		try
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=?");

				ps.setString(1, username); 
			
			ResultSet rs = ps.executeQuery();
				// if something found, username is taken
				if(rs.next())
				{
					return true;	
				}
				con.close();
		} catch (SQLException ex) {}
			return false;
	}
	
	public boolean checkLogin(String username, String password) {
		try 
		{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
			
				// replace ? values
				ps.setString(1, username); 
				ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
					
				// if something found in database table, return true
				if(rs.next())
				{
					return true;	
				}
				con.close();
		} catch (SQLException e) {}
			return false;	
	}
}

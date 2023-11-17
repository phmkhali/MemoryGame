public class User
{
	// stores username and password for account settings
	// static bc we only reference one user at a time (logged in)
	
	private static String tempUsername;
	private static String tempPassword;

	// changes the values above
	static void setUser(String username, String password)
	{
		tempUsername = username;
		tempPassword = password;
	}
	
	// returns values from above
	static String getUsername()
	{
		return tempUsername;		
	}
	
	static String getPassword()
	{
		return tempPassword;		
	}
}

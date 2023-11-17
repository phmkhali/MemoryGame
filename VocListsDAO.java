
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

// accesses lists table from database
// listsID, listName, username

public class VocListsDAO {
	
	private static int autoIncKeyFromApi; // auto increment
	private Object[]column = {"List Name", "Username"};
	private static DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	
	private static DefaultTableModel dtm = new DefaultTableModel()	
	 {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column)
	    {
	      return false; // causes all cells to be not editable
	    }
	  };
	
	
	// fill yourlists
    public void fillYourList() throws SQLException 
    {
        ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT listName FROM lists WHERE username = ?");
        ps.setString(1, User.getUsername());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String listName = rs.getString("listName");
            dlm.addElement(listName);

        }
    }

	// fill publiclists	
    public void fillPublicTable(String txtNameString) throws SQLException 
    {
        ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT listName,username FROM lists WHERE username != ? AND listName = ?");
        dtm.setColumnIdentifiers(column);
        ps.setString(1, User.getUsername());
        ps.setString(2, txtNameString);
        ResultSet rs = ps.executeQuery();	

        	while (rs.next()) 
        	{
        	 String data[] = {rs.getString("listName"),rs.getString("username")};
        		dtm.addRow(data);
        		
        	}
    }
    
    public void fillAllPublicTables() throws SQLException 
    {
    	
        ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT listName,username FROM lists WHERE username !=?");
        dtm.setColumnIdentifiers(column);
        ps.setString(1, User.getUsername());
        ResultSet rs = ps.executeQuery();

    	while (rs.next()) 
    	{
    		String data[] = {rs.getString("listName"),rs.getString("username")};
    		dtm.addRow(data);
    		
    	}

    }
    
	public static DefaultTableModel getTable()
	{
		return dtm;
	}

	public boolean checkListNameTaken(String listName) throws SQLException 
	{
		ConnectDatabase db = ConnectDatabase.getInstance();
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM lists WHERE username = ? AND listName = ?");
		ps.setString(1, User.getUsername()); 		
		ps.setString(2, listName); 	
		ResultSet rs = ps.executeQuery();	
		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public static DefaultListModel<String> getList()
	{
		return dlm;
	}

	// Button change ListName -> update new ListName in database
	public void updateListName(String name, int list_ID) throws SQLException
	{
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE lists SET listName = ? WHERE listsID = ?");

			// replace ? values
			ps.setString(1, name);
			ps.setInt(2, list_ID);
			ps.executeUpdate();
			con.close();
			System.out.println("Name of List changed");

	}
	
	public void updateListUsername(String newName, String oldName) throws SQLException
	{
		ConnectDatabase db = ConnectDatabase.getInstance();
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE lists SET username = ? WHERE username = ?");
		
		ps.setString(1, newName);
		ps.setString(2, oldName);		
		ps.executeUpdate();
		con.close();
	}


	public void addList (String listName, String username) 
	{
		try {
			
			ConnectDatabase db = ConnectDatabase.getInstance();
			Connection con = db.getConnection();
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO lists(listName, username) VALUES(?,?) ", Statement.RETURN_GENERATED_KEYS);
			
				//replace ? values
				ps.setString(1, listName);
				ps.setString(2, username);
				ps.executeUpdate();
				//set variable to current listID
				setAutoIncKeyFromApi(ps);
			} catch (SQLException e) {}    
	}		
	
	// when delete account, delete all lists under username
	public void deleteAllUserLists()
	{
		try {
		ConnectDatabase db = ConnectDatabase.getInstance();
		Connection con = db.getConnection();		
		PreparedStatement ps = con.prepareStatement("DELETE FROM lists WHERE username = ?");
		
		ps.setString(1, User.getUsername());
		ps.executeUpdate();
		con.close();
		
		} catch (SQLException e) {} 
	}
	
	// delete list that is open
	public void deleteList(String listName)
	{
		try {
		ConnectDatabase db = ConnectDatabase.getInstance();	
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM lists WHERE username = ? AND listName = ?");

		//replace ? values
		ps.setString(1, User.getUsername());
		ps.setString(2, listName);
		ps.executeUpdate();
		con.close();
		System.out.println("List deleted");
		} catch (SQLException e) {}
	}
		
	//changes variable to listId from freshly created List
	static void setAutoIncKeyFromApi(PreparedStatement ps) throws SQLException {
		// get autoincremented listID
		 autoIncKeyFromApi = -1;
	    ResultSet rs = ps.getGeneratedKeys();
	    if (rs.next()) {
	        autoIncKeyFromApi = rs.getInt(1);
	    }
		
	}
	//returns variable with listID
	static int getAutoIncKeyFromApi() throws SQLException {
		return autoIncKeyFromApi;
	}

    
}

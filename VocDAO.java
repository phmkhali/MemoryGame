import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

// DAO for list that contains all word pairs
// db table: voclist
// vocID, first_voc, sec_voc, listsID

public class VocDAO {
	
	private Object[]column = {"voc 1", "voc 2"};
	
	private static DefaultTableModel dtm = new DefaultTableModel()	
	 {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column)
	    {
	      return false; // causes all cells to be not editable
	    }
	  };
      


	public static DefaultTableModel getTable()
	{
		return dtm;
	}
	
	  
    public void loadTable (int listsID) throws SQLException
    {
            ConnectDatabase db = ConnectDatabase.getInstance();
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT first_voc, sec_voc FROM voclist WHERE listsID = ?");

            dtm.setColumnIdentifiers(column);
            ps.setInt(1, listsID);
            ResultSet rs = ps.executeQuery();	

            	while (rs.next()) 
            	{
            		String data[] = {rs.getString("first_voc"),rs.getString("sec_voc")};
            		dtm.addRow(data);
            		
            	}
            
    		rs.close();
    		ps.close();
            con.close();
      }
	
	
	// add new VocRow of the List to the database
    public void addVocs (String first_voc, String sec_voc, int listsID) throws SQLException
    {
 
            ConnectDatabase db = ConnectDatabase.getInstance();
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO voclist (first_voc, sec_voc, listsID) VALUES (?,?,?) ");

            ps.setString(1, first_voc);
            ps.setString(2, sec_voc);
            ps.setInt(3, listsID);      

            ps.executeUpdate();
            con.close();
            System.out.println("Wordpair added");
    }
    
    // delete highlighted row from jtable
    public void deleteSelected(String tableValue1, String tableValue2, int listsID) throws SQLException
    {
        ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM voclist WHERE first_voc = ? AND sec_voc = ? AND listsID = ?");
        
        ps.setString(1, tableValue1);
        ps.setString(2, tableValue2);
        ps.setInt(3, listsID);
        
        ps.executeUpdate();
        con.close();
        System.out.println("Wordpair deleted");
    }
    
    // edit highlighted row from jtable
    public void editSelected(String new1, String new2, String tableValue1, String tableValue2, int listsID) throws SQLException
    {
        ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE voclist SET first_voc = ?, sec_voc = ? WHERE first_voc = ? AND sec_voc = ? AND listsID = ?");
        
        ps.setString(1, new1);
        ps.setString(2, new2);
        ps.setString(3, tableValue1);
        ps.setString(4, tableValue2);
        ps.setInt(5, listsID);
        
        ps.executeUpdate();
        con.close();
        System.out.println("Wordpair edited");
    }

        
        static Integer getListID(String username, String listName)
        {
        	int listsID = 0;
            try {

                ConnectDatabase db = ConnectDatabase.getInstance();
                Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT listsID FROM lists WHERE username = ? AND listName = ?");

                //replace ? values
     
                ps.setString(1, username);
                ps.setString(2, listName);

    			ResultSet rs = ps.executeQuery();
    			if (rs.next()) {
    				listsID = rs.getInt("listsID");
    			}
 
                con.close();
                } catch (SQLException e) {}
        	
        	return listsID;
        }


        
}

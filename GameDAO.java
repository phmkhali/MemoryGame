
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class GameDAO 
{
	private static ArrayList<String> alWords = new ArrayList<String>();
	
	// get word pairs from db and randomly fill array
	static void fillWordList(int listsID) throws SQLException
	{	
		ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection(); 
		PreparedStatement ps = con.prepareStatement("SELECT first_voc, sec_voc FROM voclist WHERE listsID = ?");
	
		ps.setInt(1, listsID);		
		ResultSet rs = ps.executeQuery();
		
		// add all words from list to array list
	    while(rs.next())
	    {
	    	alWords.add(rs.getString("first_voc"));
	    	alWords.add(rs.getString("sec_voc"));
	    }
	    // shuffle word list
    	Collections.shuffle(alWords);
    }
	
	static void emptyWordList()
	{
		alWords.clear();
	}
	
	static ArrayList<String> getWordList()
	{
		return alWords; 
	}
	
	boolean checkMatch(String voc1, String voc2) throws SQLException
	{
		ConnectDatabase db = ConnectDatabase.getInstance();
        Connection con = db.getConnection(); 
    	PreparedStatement ps = con.prepareStatement("SELECT first_voc, sec_voc FROM voclist WHERE first_voc = ? AND sec_voc = ?");  
   	
    	// check both arrangements
    	ps.setString(1, voc1);
    	ps.setString(2, voc2);
    	ResultSet rs = ps.executeQuery();
    	
    	if(rs.next())
    	{
    		return true;
    	}
    	
    	ps.setString(1, voc2);
    	ps.setString(2, voc1);
    	ResultSet rs2 = ps.executeQuery();
  	
    	if(rs2.next())
    	{
    		return true;
    	}
		return false;
	}
	
}

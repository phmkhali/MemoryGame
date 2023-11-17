
import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;


public class VocListsHome extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JButton btnYourLists;
	private JButton btnGo;
	private String txtNameString;
	private VocListsDAO vocListsDAO = new VocListsDAO();
	
	public VocListsHome()
	{
		VocListView();
	}
	
	void VocListView()
	{
		setLayout(null);
		
		// panels
		JPanel centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel);
		centrePanel.setLayout(null);
		
		Home home = new Home();
		add(home.getMenuPanel());
		
		
		//---------
		
		JLabel header = new JLabel("VOCABULARY LISTS");
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Tahoma", Font.BOLD, 46));
		header.setBounds(89, 4, 500, 119);
		centrePanel.add(header);
		
		btnYourLists = new JButton("Your Lists");
		btnYourLists.setBackground(Color.WHITE);
		btnYourLists.addActionListener(new VocListListener());
		btnYourLists.setBounds(89, 116, 123, 30);
		centrePanel.add(btnYourLists);

		
		JLabel lblFindLists = new JLabel("FIND PUBLIC LISTS");
		lblFindLists.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblFindLists.setForeground(Color.WHITE);
		lblFindLists.setBounds(89, 194, 400, 45);
		centrePanel.add(lblFindLists);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(89, 254, 67, 21);
		centrePanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(150, 250, 197, 30);
		centrePanel.add(txtName);
		
		btnGo = new JButton("GO");
		btnGo.addActionListener(new VocListListener());
		btnGo.setBackground(Color.WHITE);
		btnGo.setBounds(375, 250, 55, 30);
		centrePanel.add(btnGo);
	}
	
	private class VocListListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();	
			txtNameString = txtName.getText();
			
			 if (e.getSource().equals(btnGo))
            {
                if (checkInput(txtNameString)) {
                    txtName.setText("");
                    
                    try {
                    	VocListsDAO.getTable().setRowCount(0); // so it isnt filled again
                        vocListsDAO.fillPublicTable(txtNameString);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    mainPanel.updatePublicListsPanel();
                }
                else // if nothing entered
                {
                    mainPanel.updatePublicListsPanel();
                    try {
                    	VocListsDAO.getTable().setRowCount(0); // so it isnt filled again
						vocListsDAO.fillAllPublicTables();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                    
                }
            }
			
			 if(e.getSource().equals(btnYourLists))
			{
				YourLists.getList().clearSelection();
				mainPanel.updateYourListsPanel();				
				// fill yourlists 
				
				try {
					VocListsDAO.getList().clear(); // so it isnt filled again					
					vocListsDAO.fillYourList();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}				
			
		}
		
	}
	public boolean checkInput(String txtName) {
        if (!txtName.isEmpty()) {
            return true;
        }
        return false;
    }
	
}

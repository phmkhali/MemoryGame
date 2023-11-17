import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JTable;

public class List extends JPanel {

	private static final long serialVersionUID = 1L;

	private	VocListsDAO vocListsDAO = new VocListsDAO();
	private	VocDAO vocDAO = new VocDAO(); 
	private JTable table;
	private static JLabel lblHeader;
	private static JButton btnAdd;
	private JButton btnPlay;
	private static JButton btnEdit;
	private static JButton btnDeleteList;
	private static JPanel centrePanel;
	
	private static JTextField txtVoc1;
	private static JTextField txtVoc2;

	private static JButton btnDeleteVoc;
	private static JButton btnChangeName;
	private static String tableValue1;
	private static String tableValue2;
	
	private static String currentList;
	private static String currentCreator;
	
	public List() 
	{
		ListView();
	
	}

	void ListView()
	{
		setLayout(null);
		
		//panels
		centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel, BorderLayout.CENTER);
		centrePanel.setLayout(null);
		
		Home home = new Home();
		add(home.getMenuPanel());
		

		//----------


		lblHeader = new JLabel("");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblHeader.setBounds(89, 5, 518, 119);
		centrePanel.add(lblHeader);
		
		// table	

		table = new JTable(VocDAO.getTable());
		ListSelectionModel listModel = table.getSelectionModel();
	    listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listModel.addListSelectionListener(new ListListener());
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.setBounds(99, 135, 323, 207);
		centrePanel.add(table);
		
		// --------------
		
		
		 btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ListListener());
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPlay.setBackground(Color.WHITE);
		btnPlay.setBounds(463, 135, 103, 45);
		centrePanel.add(btnPlay);
		
		
		 btnDeleteVoc = new JButton("<html><center>Delete<br>Row");
		btnDeleteVoc.addActionListener(new ListListener());
		btnDeleteVoc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteVoc.setBackground(Color.WHITE);
		btnDeleteVoc.setBounds(463, 244, 103, 36);

		 btnDeleteList = new JButton("Delete List");
		btnDeleteList.addActionListener(new ListListener());
		btnDeleteList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteList.setBackground(Color.WHITE);
		btnDeleteList.setBounds(463, 291, 103, 36);
		
		 btnChangeName = new JButton("<html><center>Change<br>List Name");
		btnChangeName.addActionListener(new ListListener());
		btnChangeName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnChangeName.setBackground(Color.WHITE);
		btnChangeName.setBounds(463, 197, 103, 36);
		
		txtVoc1 = new JTextField();
		txtVoc1.addFocusListener(new ListListener());
		txtVoc1.setForeground(Color.DARK_GRAY);
		txtVoc1.setToolTipText("enter vocabulary");
		txtVoc1.setBounds(100, 364, 156, 28);
		txtVoc1.setColumns(10);
		
		txtVoc2 = new JTextField();
		txtVoc2.addFocusListener(new ListListener());
		txtVoc2.setForeground(Color.DARK_GRAY);
		txtVoc2.setToolTipText("enter vocabulary");
		txtVoc2.setColumns(10);
		txtVoc2.setBounds(266, 364, 156, 28);

		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ListListener());
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(463, 377, 103, 15);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ListListener());
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setBounds(463, 358, 103, 15);		
		
		
		centrePanel.add(btnDeleteVoc);
		centrePanel.add(btnDeleteList);
		centrePanel.add(btnEdit);
		centrePanel.add(btnAdd);
		centrePanel.add(txtVoc2);
		centrePanel.add(txtVoc1);
		centrePanel.add(btnChangeName);	

	}
	
	private class ListListener implements ActionListener, ListSelectionListener, FocusListener
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			MainPanel mainPanel = Main.getMainPanel();
						
		 if(e.getSource().equals(btnChangeName))
			{
				String name = JOptionPane.showInputDialog("Enter a name for your list. The name cannot be empty");
				
				if(name == null || (name != null && ("".equals(name)))) {
					System.out.println("list name changed failed");
				}
		
				else {
					changeListName(name);
					try {
						vocListsDAO.updateListName(name, VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					YourLists.setSelectedVocList(name);	
					List.setCurrentList(name);
				}
				
			}
			 if(e.getSource().equals(btnPlay))
			{
				// only playable if 10 word pairs in list, 20 memory cards
				if(VocDAO.getTable().getRowCount()!=10)
				{
					JOptionPane.showMessageDialog(null, "Currently the Memory Game is only playable if you have ten word pairs in your list!");
				}
				else
				{
					Game.setListName(lblHeader.getText(),currentCreator);
					mainPanel.updateGamePanel();
				}
			}
			
			 if(e.getSource().equals(btnDeleteVoc))
			{
				try {
					// delete selected row in database and reload table
					vocDAO.deleteSelected(tableValue1, tableValue2, VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
					
							// remove the selected row from the table model			
							VocDAO.getTable().removeRow(table.getSelectedRow());
							table.clearSelection();
							txtVoc1.setText("");
							txtVoc2.setText("");
							JOptionPane.showMessageDialog(null, "Deleted successfully");
							VocDAO.getTable().setRowCount(0);
							vocDAO.loadTable(VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
						 
					}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		
			
			 if(e.getSource().equals(btnDeleteList)) 
			{
				vocListsDAO.deleteList(YourLists.getSelectedVocList());
				mainPanel.updateYourListsPanel();
				try {
					VocListsDAO.getList().clear();
					vocListsDAO.fillYourList();
					txtVoc1.setText("");
					txtVoc2.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			 if(e.getSource().equals(btnEdit))
			{
				try {
					// edits pair in database and reloads
					vocDAO.editSelected(txtVoc1.getText(), txtVoc2.getText(), tableValue1, tableValue2, VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
					VocDAO.getTable().setRowCount(0);
					vocDAO.loadTable(VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
					txtVoc1.setText("");
					txtVoc2.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			 if(e.getSource().equals(btnAdd))
			{
				// doesnt interfere with edit 
				table.clearSelection();
				
				try {
					if(txtVoc1.getText().equals("") || txtVoc2.getText().equals("")) // if one of the textfields is empty
					{
						JOptionPane.showMessageDialog(null, "The text fields cannot be empty!");
					}
					// only 10 pairs allowed for game
					else if(VocDAO.getTable().getRowCount()>9)
					{
						JOptionPane.showMessageDialog(null, "You cannot add more than ten word pairs!");
						txtVoc1.setText("");
						txtVoc2.setText("");
					}
					
					// add pair to database and clear txtfields
					else
					{								
						vocDAO.addVocs(getTxtInput1(),getTxtInput2(), VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
						txtVoc1.setText("");
						txtVoc2.setText("");
					}
					
					// clear and reload table with database data
					VocDAO.getTable().setRowCount(0);
					vocDAO.loadTable(VocDAO.getListID(User.getUsername(), YourLists.getSelectedVocList()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	
	
	}

		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
			// display selected row in textfields
		    if (!e.getValueIsAdjusting()) 
		    {	    
				if(table.getSelectedRow() != -1)
				{
					txtVoc1.setText("");
					txtVoc2.setText("");
					
					
				// select a row from table and display the values in the textfields
		    	tableValue1 = VocDAO.getTable().getValueAt(table.getSelectedRow(),0).toString();
		    	tableValue2 = VocDAO.getTable().getValueAt(table.getSelectedRow(),1).toString();

		    		txtVoc1.setText(tableValue1);
		    		txtVoc2.setText(tableValue2);
				}
		    }
		}

		@Override
		// remove textfield data when clicked
		public void focusGained(FocusEvent e) 
		{
			if(e.getSource().equals(txtVoc1))
			{
				txtVoc1.setText("");
			}
			else if(e.getSource().equals(txtVoc2)) 
			{
				txtVoc2.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) 
		{		

		}
	
	}

		public static void changeListName(String name) {
			lblHeader.setText(name);		
		}
		
		// current stuff
		public static void setCurrentList(String name)
		{
			currentList = name;
		}
		
		public static void setCurrentCreator(String name)
		{
			currentCreator = name;
		}
		
		public static String getCurrentList()
		{
			return currentList;
		}
		
		public static String getCurrentCreator()
		{
			return currentCreator;
		}
	
		
		
	static String getTxtInput1()
	{
		return txtVoc1.getText();
	}
	static String getTxtInput2()
	{
		return txtVoc2.getText();
	}
	
	// switch between public and private view
	static void showButtons()
	{
		btnDeleteVoc.setVisible(true);
		btnDeleteList.setVisible(true);
		btnEdit.setVisible(true);
		btnAdd.setVisible(true);
		txtVoc2.setVisible(true);
		txtVoc1.setVisible(true);
		btnChangeName.setVisible(true);
	}
	
	static void hideButtons()
	{
		btnDeleteVoc.setVisible(false);
		btnDeleteList.setVisible(false);
		btnEdit.setVisible(false);
		btnAdd.setVisible(false);
		txtVoc2.setVisible(false);
		txtVoc1.setVisible(false);
		btnChangeName.setVisible(false);
	}


		

}
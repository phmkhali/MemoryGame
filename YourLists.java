import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

// panel that shows all the lists belonging to the logged in user

public class YourLists extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnCreateList;
	private JButton btnSelect;

	private static JList<String> jlVocList;
	private VocListsDAO vocListsDAO = new VocListsDAO();
	private VocDAO vocDAO = new VocDAO();
	private String createListInput;
	
	private static String selectedVocList;
	

	public YourLists() 
	{
		YourListsView();	
	}

	void YourListsView()
	{
		setLayout(null);
		
		//panels
		JPanel centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel, BorderLayout.CENTER);
		centrePanel.setLayout(null);
		
		Home home = new Home();
		add(home.getMenuPanel());
		//----------

		JLabel lblHeader = new JLabel("YOUR LISTS");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblHeader.setBounds(89, 5, 518, 119);
		centrePanel.add(lblHeader);
		
		btnCreateList = new JButton("Create New List");
		btnCreateList.addActionListener(new YourListListener());
		btnCreateList.setBackground(Color.WHITE);
		btnCreateList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCreateList.setBounds(281, 365, 144, 31);
		centrePanel.add(btnCreateList);
		
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(new YourListListener());
		btnSelect.setBackground(Color.WHITE);
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelect.setBounds(99, 365, 144, 31);
		centrePanel.add(btnSelect);

	
		jlVocList = new JList<String>(VocListsDAO.getList());		
		jlVocList.addListSelectionListener(new YourListListener());
		jlVocList.clearSelection();
		jlVocList.setVisibleRowCount(10);
		jlVocList.setBounds(99, 118, 402, 228);
		centrePanel.add(jlVocList); 

	}
	
	private class YourListListener implements ActionListener, ListSelectionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		MainPanel mainPanel = Main.getMainPanel();		
			 if(e.getSource().equals(btnCreateList))
			{
				
				try {
						do 
						{
							createListInput = JOptionPane.showInputDialog("Enter a new name for your list. You cannot have two lists with the same name.");	
						}
							while (vocListsDAO.checkListNameTaken(createListInput));
						
						// cancel option
						if(createListInput == null || (createListInput != null && ("".equals(createListInput))))   
						{
						   System.out.println("create new list cancelled");
						}
						else
						{
							List.changeListName(createListInput);					
							vocListsDAO.addList(createListInput, User.getUsername());
							vocListsDAO.updateListName(createListInput, VocListsDAO.getAutoIncKeyFromApi());
							selectedVocList = createListInput;
							
							List.setCurrentList(selectedVocList);
							List.setCurrentCreator(User.getUsername());
							
							VocDAO.getTable().setRowCount(0);
							vocDAO.loadTable(VocDAO.getListID(User.getUsername(), selectedVocList));
							mainPanel.updateListPanel();
						}
					
					} catch (HeadlessException e1) {
					e1.printStackTrace();
					} catch (SQLException e1) {
					e1.printStackTrace();
					}
	
			}
			
			else if(e.getSource().equals(btnSelect))
			{
				try {			
					if(!(getSelectedVocList()==null))
					{
						List.showButtons();
						VocDAO.getTable().setRowCount(0);
						vocDAO.loadTable(VocDAO.getListID(User.getUsername(), selectedVocList));
						
						List.setCurrentList(selectedVocList);
						List.setCurrentCreator(User.getUsername());
						
						mainPanel.updateListPanel();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Select a vocabulary list!");						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				List.changeListName(selectedVocList);
				}
			
	     }
	
		// select list
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{		
		     if (!e.getValueIsAdjusting()) {
				selectedVocList = (String) jlVocList.getSelectedValue();
				System.out.println(selectedVocList);				
		     }

		}	

	}
	
	static String getSelectedVocList()
	{
		return selectedVocList;
	}
	static void setSelectedVocList(String name)
	{
		selectedVocList = name;
	}
	
	static JList<String> getList()
	{
		return jlVocList;
	
	}

}

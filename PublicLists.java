
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PublicLists extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private VocDAO vocDAO = new VocDAO();
	private JButton btnSelect;
	private static String selectedVocListName;
	private static String selectedVocListCreator;
	
	
	public PublicLists() 
	{
		PublicListsView();
	}

	void PublicListsView()
	{
		setLayout(null);
		
		//panels
		JPanel centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel);
		centrePanel.setLayout(null);
		
		Home home = new Home();
		add(home.getMenuPanel());
		//----------

		//------------

		JLabel lblHeader = new JLabel("PUBLIC LISTS");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblHeader.setBounds(89, 5, 518, 119);
		centrePanel.add(lblHeader);
	
		table = new JTable(VocListsDAO.getTable());
		ListSelectionModel listModel = table.getSelectionModel();
	    listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listModel.addListSelectionListener(new PublicListListener());
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.setBounds(99, 118, 402, 228);
		centrePanel.add(table);
		
		
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(new PublicListListener());
		btnSelect.setBackground(Color.WHITE);
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelect.setBounds(99, 365, 144, 31);
		centrePanel.add(btnSelect);

	}
	
	private class PublicListListener implements ActionListener, ListSelectionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();

			 if(e.getSource().equals(btnSelect))
			{				
				if(!(getSelectedVocListName()==null))
				{
					List.changeListName(selectedVocListName);
					List.hideButtons();
					System.out.println("public view");
					VocDAO.getTable().setRowCount(0);
					
					List.setCurrentList(selectedVocListName);
					List.setCurrentCreator(selectedVocListCreator);

					try {
						vocDAO.loadTable(VocDAO.getListID(selectedVocListCreator, selectedVocListName));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainPanel.updateListPanel();
					table.clearSelection();
				}		
				else
				{
					JOptionPane.showMessageDialog(null, "Select a vocabulary list!");
				}
			}
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
		     if (!e.getValueIsAdjusting()) 
		     {
		    	 if(table.getSelectedRow() != -1)
		    	 {
					selectedVocListName = VocListsDAO.getTable().getValueAt(table.getSelectedRow(),0).toString();
					selectedVocListCreator = VocListsDAO.getTable().getValueAt(table.getSelectedRow(),1).toString();

					System.out.println(selectedVocListName);	
		    	 }
			 }
		}
		
		static String getSelectedVocListName()
		{
			return selectedVocListName;
		}

	
}
}


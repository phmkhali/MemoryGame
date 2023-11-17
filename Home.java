import javax.swing.*;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.*;

public class Home extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JButton btnVocList;
	private JButton btnHome;
	private JButton btnAccount;
	private JPanel menuPanel;
	
    public Home()
    {
    	HomeView();
	}
    
    void HomeView()
    {
    	setLayout(null);
    	
    	// panels
    	JPanel centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel);
		centrePanel.setLayout(null);
		
		menuPanel();
		add(getMenuPanel());
		
		// -------------
		
		JLabel lblTitle = new JLabel("<html><center>Memory<br/> Vocabulary Trainer");
		lblTitle.setBounds(87, 91, 458, 183);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblTitle.setForeground(Color.WHITE);
		centrePanel.add(lblTitle);
		
		JLabel lblSubtitle = new JLabel("<html><center>(select or create a Vocabulary List to start!)");
		lblSubtitle.setForeground(Color.WHITE);
		lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblSubtitle.setBounds(129, 231, 391, 93);
		centrePanel.add(lblSubtitle);
    }
    
    // generates menu on left hand side, used in all views
    public void menuPanel()
    {
		  class HomeListener implements ActionListener {
	    		@Override
	    		public void actionPerformed(ActionEvent e)	{
	    			MainPanel mainPanel = Main.getMainPanel();	
	    			if(e.getSource().equals(btnVocList)) {
	    				mainPanel.updateVocListPanel();
	    			}
	    			if(e.getSource().equals(btnAccount)) {
	    				mainPanel.updateAccountPanel();
	    			}
	    			
	    		if(e.getSource().equals(btnHome)) {
	    			mainPanel.updateHomePanel();
	    			}}}
		  
   	 	menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 131, 465);
		menuPanel.setBackground(Color.GRAY);
		menuPanel.setLayout(null);

		btnVocList = new JButton("Vocabulary Lists");
		btnVocList.addActionListener(new HomeListener());
		btnVocList.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnVocList.setBackground(Color.WHITE);
		btnVocList.setBounds(10, 140, 115, 38);
		btnVocList.setForeground(Color.BLACK);
		menuPanel.add(btnVocList);
	
		btnHome = new JButton("Return to Home");
		btnHome.addActionListener(new HomeListener());
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnHome.setBackground(Color.WHITE);
		btnHome.setBounds(10, 189, 115, 38);
		btnHome.setForeground(Color.BLACK);
		menuPanel.add(btnHome);
	
		btnAccount = new JButton("Account Settings");
		btnAccount.addActionListener(new HomeListener());
		btnAccount.setBackground(Color.WHITE);
		btnAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAccount.setBounds(10, 238, 115, 38);
		btnAccount.setForeground(Color.BLACK);
		menuPanel.add(btnAccount);
    }
    
    public JPanel getMenuPanel()
    {
    	return menuPanel;
    }
    

}

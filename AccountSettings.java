
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccountSettings extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// static so it can be accessed from LoginPanel
	private static JTextField txtUsername;
	private static JTextField txtPassword;

	private JButton btnLogout;
	private JButton btnDelete;
	private JButton btnSaveUsername;
	private JButton btnSavePassword;
	
	private UserDAO userDAO = new UserDAO();
	private VocListsDAO vocListsDAO = new VocListsDAO();

	public AccountSettings() 
	{
		AccountView();
	}
	
	void AccountView()
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

		//---------

		txtUsername = new JTextField();
		txtUsername.setText("");
		txtUsername.setBounds(214, 147, 194, 30);
		centrePanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("");
		txtPassword.setBounds(214, 215, 194, 30);
		centrePanel.add(txtPassword);
		txtPassword.setColumns(10);
		

		JLabel lblEnterUsername = new JLabel("Username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEnterUsername.setForeground(Color.WHITE);
		lblEnterUsername.setBounds(89, 134, 181, 48);
		centrePanel.add(lblEnterUsername);
		
		JLabel lblEnterPassword = new JLabel("Password:");
		lblEnterPassword.setBounds(89, 211, 164, 30);
		centrePanel.add(lblEnterPassword);
		lblEnterPassword.setForeground(Color.WHITE);
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		btnLogout = new JButton("Logout");
		btnLogout.setBackground(Color.WHITE);
		btnLogout.addActionListener(new AccountListener());
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogout.setBounds(89, 283, 131, 30);
		centrePanel.add(btnLogout);
		
		JLabel lblHeader = new JLabel("ACCOUNT SETTINGS");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblHeader.setBounds(89, 5, 518, 119);
		centrePanel.add(lblHeader);
		
		btnDelete = new JButton("Delete Account");
		btnDelete.setBackground(Color.WHITE);
		btnDelete.addActionListener(new AccountListener());
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelete.setBounds(276, 283, 131, 30);
		centrePanel.add(btnDelete);
		
		btnSaveUsername = new JButton("save");
		btnSaveUsername.setBackground(Color.WHITE);
		btnSaveUsername.addActionListener(new AccountListener());
		btnSaveUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSaveUsername.setBounds(447, 146, 64, 30);
		centrePanel.add(btnSaveUsername);
		
		btnSavePassword = new JButton("save");
		btnSavePassword.setBackground(Color.WHITE);
		btnSavePassword.addActionListener(new AccountListener());
		btnSavePassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSavePassword.setBounds(447, 214, 64, 30);
		centrePanel.add(btnSavePassword);
		
		JLabel lblInstructions = new JLabel("<html>To change your Username and/or Password, simply edit the values in the text fields.<br>Click the \"save\" button to keep your changes.");
		lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInstructions.setForeground(Color.WHITE);
		lblInstructions.setBounds(89, 342, 504, 39);
		centrePanel.add(lblInstructions);
	}
	private class AccountListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();	

			// logout and return to homepanel
			if(e.getSource().equals(btnLogout))
				{
					mainPanel.updateLoginPanel();
					JOptionPane.showMessageDialog(null, "You are now logged out.");
				}
			
			// delete account
			// pop up to ask for confirmation
			if(e.getSource().equals(btnDelete))
			{
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete your account?","Warning",JOptionPane.YES_NO_OPTION);
				
				// if pop up = yes, connect to database and delete account + lists connected to account
				if(dialogResult == JOptionPane.YES_OPTION)
				{			
					userDAO.delete(User.getUsername());
					vocListsDAO.deleteAllUserLists();
					// after delete, return to loginpanel and get notification window 
					mainPanel.updateLoginPanel();
					JOptionPane.showMessageDialog(null, "Your account has been deleted!");
				}
			}
			
			// change username
			if(e.getSource().equals(btnSaveUsername))
			{
								
					// if username taken
						if(userDAO.checkUsernameTaken(txtUsername.getText()))
						{
							JOptionPane.showMessageDialog(null, "This username is already taken.");
							// reset
							setTxtUsername(User.getUsername());						
						}
						
						// if username available, change data in database
						else
						{		
							userDAO.updateUsername(txtUsername.getText());
							try {
								vocListsDAO.updateListUsername(txtUsername.getText(), User.getUsername());
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Your username has been changed.");
							setTxtUsername(txtUsername.getText());
							User.setUser(txtUsername.getText(), User.getPassword());
						}
			}

			
			
			// same thing like change username
			if(e.getSource().equals(btnSavePassword))
			{
				// change password
				if(!txtPassword.getText().isEmpty())
				{
					userDAO.updatePassword(txtPassword.getText());	
					JOptionPane.showMessageDialog(null, "Your password has been changed.");
					setTxtPassword(txtPassword.getText());
					User.setUser(User.getUsername(), txtPassword.getText());
				}
				
				else
				{
					JOptionPane.showMessageDialog(null, "Enter a password.");
					// reset
					setTxtUsername(User.getPassword());	
				}
			}
		}
	}

	// change text in txtfields
	// can be accessed from other classes too (used for Login)
	static void setTxtUsername(String username)
	{
		txtUsername.setText(username);		
	}
	static void setTxtPassword(String password) 
	{
		txtPassword.setText(password);
	}

}
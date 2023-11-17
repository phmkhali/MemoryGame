
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected JTextField txtUsername;
	protected JPasswordField pwField;
	private JButton btnLogin;
	private JButton btnSignup;	
	private String usernameString, passwordString;
	UserDAO userDAO = new UserDAO();

	public Login() 
	{	
		LoginView();
	}
	
	void LoginView()
	{	
		setLayout(null);
		
		//panels
		JPanel centrePanel = new JPanel();
		centrePanel.setBounds(131, 0, 645, 465);
		centrePanel.setBackground(Color.DARK_GRAY);
		add(centrePanel);
		centrePanel.setLayout(null);
		
		JPanel westPanel = new JPanel();
		westPanel.setBounds(0, 0, 131, 465);
		add(westPanel);
		westPanel.setBackground(Color.GRAY);
		westPanel.setLayout(null);
		//-----------------
		
		//menu
		JLabel lblMenuTitle = new JLabel("<html><center>Memory</br> Vocabulary </br>Trainer");
		lblMenuTitle.setForeground(Color.WHITE);
		lblMenuTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenuTitle.setBounds(10, 122, 115, 176);
		westPanel.add(lblMenuTitle);
		//-----------------
		
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(243, 146, 197, 30);
		centrePanel.add(txtUsername);
		
		pwField = new JPasswordField();
		pwField.setBounds(243, 214, 197, 30);
		centrePanel.add(pwField);
		pwField.setColumns(10);
		
		JLabel lblEnterUsername = new JLabel("Enter Username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEnterUsername.setForeground(Color.WHITE);
		lblEnterUsername.setBounds(89, 134, 152, 48);
		centrePanel.add(lblEnterUsername);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		lblEnterPassword.setBounds(89, 211, 135, 30);
		centrePanel.add(lblEnterPassword);
		lblEnterPassword.setForeground(Color.WHITE);
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		btnLogin = new JButton("Login");
		btnLogin.setBackground(Color.WHITE);
		btnLogin.addActionListener(new LoginListener());	
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(89, 299, 123, 30);
		centrePanel.add(btnLogin);
		
		btnSignup = new JButton("Sign-up");
		btnSignup.setBackground(Color.WHITE);
		btnSignup.addActionListener(new LoginListener());
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSignup.setBounds(243, 299, 123, 30);
		centrePanel.add(btnSignup);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblLogin.setBounds(89, 4, 177, 119);
		centrePanel.add(lblLogin);
	}
	
	private class LoginListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();	
			
			// get values from textfields
			usernameString = txtUsername.getText();
			passwordString = String.valueOf(pwField.getPassword());		

			if(e.getSource().equals(btnLogin))
			{
					// if credentials match, go to homepage
					// clear txtfields
					try {
						if(userDAO.checkLogin(usernameString, passwordString))
						{
							JOptionPane.showMessageDialog(null, "Login successful!");

								User.setUser(usernameString, passwordString);
								// add accountpanel txtfields
								AccountSettings.setTxtUsername(User.getUsername());
								AccountSettings.setTxtPassword(User.getPassword());
							
							System.out.println("as: " +User.getUsername()+", " + User.getPassword());
							
							txtUsername.setText("");
							pwField.setText("");
							
							mainPanel.updateHomePanel();
						}
						// if credentials dont match
						else
						{
							JOptionPane.showMessageDialog(null, "Please enter a valid Username and Password!");
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					}
			}
			
			// signup button
			else
			{
				txtUsername.setText("");	
				pwField.setText("");
				mainPanel.updateSignupPanel();		
			}
		}	
		
	}
}

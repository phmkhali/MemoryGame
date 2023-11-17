
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Signup extends JPanel
{

	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;
	private JPasswordField pwField;
	private JButton btnSignup;
	private JButton btnReturnToLogin;
	
	private String usernameString, passwordString;
	
	UserDAO userDAO = new UserDAO();

	public Signup() 
	{	
		SignupView();
	}
	
	void SignupView()
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
		//---------
		
		//menu
		JLabel lblMenuTitle = new JLabel("<html><center>Memory</br> Vocabulary </br>Trainer");
		lblMenuTitle.setForeground(Color.WHITE);
		lblMenuTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenuTitle.setBounds(10, 122, 115, 176);
		westPanel.add(lblMenuTitle);
		//--------
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(290, 147, 197, 30);
		centrePanel.add(txtUsername);
		
		pwField = new JPasswordField();
		pwField.setBounds(290, 215, 197, 30);
		centrePanel.add(pwField);
		pwField.setColumns(10);
		
		JLabel lblEnterUsername = new JLabel("Choose a username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEnterUsername.setForeground(Color.WHITE);
		lblEnterUsername.setBounds(89, 134, 181, 48);
		centrePanel.add(lblEnterUsername);
		
		JLabel lblEnterPassword = new JLabel("Choose a password:");
		lblEnterPassword.setBounds(89, 211, 164, 30);
		centrePanel.add(lblEnterPassword);
		lblEnterPassword.setForeground(Color.WHITE);
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		btnSignup = new JButton("Create account");
		btnSignup.setBackground(Color.WHITE);
		btnSignup.addActionListener(new SignupListener());
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSignup.setBounds(89, 298, 131, 30);
		centrePanel.add(btnSignup);
		
		JLabel lblLogin = new JLabel("SIGN-UP\r\n");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblLogin.setBounds(89, 5, 242, 119);
		centrePanel.add(lblLogin);
		
		btnReturnToLogin = new JButton("Return to Login");
		btnReturnToLogin.setBackground(Color.WHITE);
		btnReturnToLogin.addActionListener(new SignupListener());
		btnReturnToLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReturnToLogin.setBounds(290, 298, 131, 30);
		centrePanel.add(btnReturnToLogin);
	}
	
	private class SignupListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();	
			usernameString = txtUsername.getText();
			passwordString = String.valueOf(pwField.getPassword());
			
			if(e.getSource().equals(btnSignup))
				{
				if (checkInput(usernameString, passwordString)) // anything entered?
				{
						if(!userDAO.checkUsernameTaken(usernameString)) // if username not taken, add user and continue 
						{
							JOptionPane.showMessageDialog(null, "Sign-Up successful! Use Login to continue.");
							userDAO.add(usernameString, passwordString);
							mainPanel.updateLoginPanel();
							txtUsername.setText("");
							pwField.setText("");
						}
						else // if username taken, error and clear txtfields
						{
							JOptionPane.showMessageDialog(null, "The username is already taken.");
							txtUsername.setText("");
							pwField.setText("");
						}	
				}	
					else // if nothing entered
					{
						JOptionPane.showMessageDialog(null, "Enter a username and/or password!");
					}
					
				}
			// button return to login
			else if (e.getSource().equals(btnReturnToLogin)) 
			{
				mainPanel.updateLoginPanel();
				txtUsername.setText("");
				pwField.setText("");
			}
			
		}	
	}

	public boolean checkInput(String username, String password) 
	{
		if(!username.isEmpty()&&!password.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	


}

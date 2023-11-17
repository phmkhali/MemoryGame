import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class MainPanel extends JPanel 
{
	
	// main container of other panels
	// tabbedpane switches between panels if requested
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel login;
	private JPanel signup;
	private JPanel home;
	private JPanel vocList;
	private JPanel account;
	private JPanel yourLists;
	private JPanel publicLists;
	private JPanel list;
	private JPanel game;
	
	// create tabbedpane & instance of subpannel
	public MainPanel()
	{
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// hide tabs from tabbedpane
		tabbedPane.setUI(new BasicTabbedPaneUI() 
		{  
		    @Override  
		    protected int calculateTabAreaHeight(int tab_placement, int run_count, int max_tab_height) 
		    {  
		            return 0;  
		    }  
		}); 
				
		login = new Login();
		signup = new Signup();
		home = new Home();
		vocList = new VocListsHome();
		account = new AccountSettings();
		yourLists = new YourLists();
		publicLists = new PublicLists();	
		game = new Game();
		list = new List();
		
		// always open loginPanel when running the code
		tabbedPane.addTab("Login", login);
		tabbedPane.setSelectedIndex(0);		
		add(tabbedPane,BorderLayout.CENTER);
	}	

	// only display one tab at a time 
	void tabOrder()
	{
		tabbedPane.setSelectedIndex(1);
		tabbedPane.removeTabAt(0);
	}

	// switch between all subpanels, 
	// can be called from controller of subclasses
	public void updateLoginPanel()
	{
		tabbedPane.addTab("Login", login);	
		tabOrder();
	}	
	public void updateHomePanel() 
	{
		tabbedPane.addTab("Home", home);
		tabOrder();
	}	
	public void updateVocListPanel()
	{
		tabbedPane.addTab("Vocabulary Lists", vocList);
		tabOrder();
	}	
	public void updateSignupPanel()
	{
		tabbedPane.addTab("Sign-Up", signup);
		tabOrder();
	}	
	public void updateAccountPanel()
	{
		tabbedPane.addTab("Account Settings", account);
		tabOrder();
	}	
	public void updateYourListsPanel()
	{
		tabbedPane.addTab("Your Lists", yourLists);
		tabOrder();
	}	
	public void updatePublicListsPanel()
	{
		tabbedPane.addTab("Public Lists", publicLists);
		tabOrder();
	}
	public void updateGamePanel()
	{
		tabbedPane.addTab("Memory Game", game);
		tabOrder();
	}
	public void updateListPanel()
	{
		tabbedPane.addTab("List", list);
		tabOrder();
	}
}

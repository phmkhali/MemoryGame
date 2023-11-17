
import java.sql.SQLException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblHeader;
	private JLabel lblScore;
	private static JLabel lblListName;
	private static JButton[] fieldArray;
	private JButton btnStart;
	private JButton btnExit;
	private static JPanel fieldPanel;
	private int score = 0;
		// temporarily stores two chosen buttons
	private int c1 = 21;
	private int c2 = 21;
	private GameDAO gameDAO = new GameDAO();
	
	//timer
    private static JLabel lblTime = new JLabel();
    private static int elapsedTime = 0;
    private static int seconds = 0;
    private static int minutes = 0;
    private static String seconds_string = String.format("%02d", seconds); // format the integer with 2 digits, left padding it with zeroes
    private static String minutes_string = String.format("%02d", minutes);
    private boolean started = false;
    private static Timer timer;

	public Game() 
	{
		GameView();	
	}

	void GameView()
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

		//----------

		JLabel lblMenuTitle = new JLabel("<html><center>Memory</br> Vocabulary </br>Trainer");
		lblMenuTitle.setForeground(Color.WHITE);
		lblMenuTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenuTitle.setBounds(10, 122, 115, 176);
		westPanel.add(lblMenuTitle);

		 lblHeader = new JLabel("PLAY MEMORY");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 46));
		lblHeader.setBounds(89, 5, 518, 119);
		centrePanel.add(lblHeader);
		
		 lblListName = new JLabel("");
		lblListName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblListName.setForeground(Color.WHITE);
		lblListName.setBounds(89, 110, 400, 20);
		centrePanel.add(lblListName);		

		 btnStart = new JButton("START");
		btnStart.addActionListener(new GameListener());
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnStart.setBackground(Color.WHITE);
		btnStart.setBounds(504, 151, 103, 36);
		centrePanel.add(btnStart);
		
		btnExit = new JButton("Exit Game");
		btnExit.addActionListener(new GameListener());
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(504, 198, 103, 36);
		centrePanel.add(btnExit);
		

		 lblScore = new JLabel("Score: "+score);
		lblScore.setForeground(Color.WHITE);
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblScore.setBounds(504, 285, 131, 14);
		centrePanel.add(lblScore);

		// timer 
		timer = new Timer(1000, new TimerListener());		
		lblTime.setText(minutes_string+":"+seconds_string);
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTime.setBounds(504, 260, 131, 14);
		centrePanel.add(lblTime);

		
		// playing field, auto. distributes jbuttons
		fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(5, 5, 5, 5));
		fieldPanel.setBounds(89, 151, 368, 234);
		fieldPanel.setBackground(Color.DARK_GRAY);
		centrePanel.add(fieldPanel);	
		
		// button array with 20 buttons
		fieldArray = new JButton[20];	
		for (int i = 0; i < 20; i++)
		{			
				fieldArray[i] = new JButton();
				fieldArray[i].addActionListener(new GameListener());
				fieldArray[i].setEnabled(false);
				fieldPanel.add(fieldArray[i]);				
		}
	}
	
	private class GameListener implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			MainPanel mainPanel = Main.getMainPanel();
			 if(e.getSource().equals(btnExit))
				{
					if(confirmExit())
					mainPanel.updateHomePanel();
				}
				
		 if(e.getSource().equals(btnStart))
			{
				System.out.println("Game started");
				try {					
					GameDAO.emptyWordList();
					GameDAO.fillWordList(VocDAO.getListID(List.getCurrentCreator(), List.getCurrentList()));
		
					addWordlistToField();
					clearField(); // hide the words

					System.out.println("Word List: " + GameDAO.getWordList().toString());
					System.out.println(GameDAO.getWordList().size());
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				// start timer				
				   if(started==false) 
				   {
					    started=true;
					    btnStart.setEnabled(false);
					    start();
				   }					
			}		
			
			// ----------------------- gameplay when button is pressed --------------------------------
			for(int i=0;i<20;i++)
			{
				if(e.getSource().equals(fieldArray[i]))
				{		
					 // first card
					if(c1==21 && c2==21) { 
					flipCard(i);					
					c1 = i;		
					fieldArray[c1].setEnabled(false);
					System.out.println(fieldArray[i].getText());
					}
					
					 // second card
					else if(c1!=21 && c2==21) {
						flipCard(i);
						c2 = i;	
						System.out.println(fieldArray[i].getText());
					}
					
					 // if two cards selected 
					else if(c1!=21 && c2!=21) {
						try {
							if(isMatch(c1, c2)) { // check for match
								score++;
								lblScore.setText("Score: " + score);
								fieldArray[c1].setBackground(Color.LIGHT_GRAY);
								fieldArray[c2].setBackground(Color.LIGHT_GRAY);
								fieldArray[c2].setEnabled(false);
								c1=21;
								c2=21;
							}
							else { // if not match reset
								flipCard(c1);
								flipCard(c2);
								fieldArray[c1].setEnabled(true);
								c1=21;
								c2=21;
							}													
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					
					if(score==10) // if all pairs found
					{
						stop();
						System.out.println("game ended");
						JOptionPane.showMessageDialog(null, "You finished this Memory in: "+getTime()+"s.");
						mainPanel.updateListPanel();
						
						 // reset field for next game
						clearField();
						score=0;
						started = false;
						btnStart.setEnabled(true);
						lblScore.setText("Score: "+score);
						reset();
					}					
				}		 	
			}
			
			// ----------------------- gameplay when button is pressed --------------------------------
		}		
	}
	
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)  // what timer does every 1000ms
		{
			elapsedTime=elapsedTime+1000;
			minutes = (elapsedTime/60000) % 60;  
			seconds = (elapsedTime/1000) % 60; // doesnt display anything above 60
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);	 
			lblTime.setText(minutes_string+":"+seconds_string);
		}
	}
	
	// fill empty button array with word list from GameDAO
	static void addWordlistToField() throws SQLException
	{
		int counter = 0; // iterates through wordlist
		
		for (int i = 0; i < 20; i++) // iterates through button array
		{			
				fieldArray[i].setText(GameDAO.getWordList().get(counter));
				fieldArray[i].setEnabled(true);
				fieldArray[i].setBackground(Color.WHITE);
				fieldArray[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
				fieldPanel.add(fieldArray[i]);	
				counter++;			
		}	
	}	
	
	// clear text on jbuttons
	void clearField()
	{
		for (int i = 0; i < 20; i++) // iterates through button array
		{
			fieldArray[i].setText("");		
			fieldArray[i].setBackground(Color.WHITE);
		}			
	}

	// show or hide card
	void flipCard(int pos)
	{	
		if(fieldArray[pos].getText()=="")
		{
			fieldArray[pos].setText(GameDAO.getWordList().get(pos)); // reveal 
		}
		else // if has text
		{
			fieldArray[pos].setText(""); // hide 
		}
	}

	boolean isMatch(int c1, int c2) throws SQLException
	{
		if(gameDAO.checkMatch(fieldArray[c1].getText(),fieldArray[c2].getText()))
			{
				return true;
			}
		return false;
	}
	
	
	// --------------- timer -------------------
	
	static void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void reset() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;

        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        lblTime.setText(minutes_string + ":" + seconds_string);
    }   
    
    public String getTime()
    {
    	return minutes_string+":"+seconds_string;
    }
    
	// --------------- timer -------------------
    
    boolean confirmExit()
    {
    	int dialogResult = JOptionPane.showConfirmDialog(null, "You will lose all your progress. Are you sure you want to exit the game?","Warning", JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION)
			{
				// reset the game
				stop();
				reset();
				clearField();
				score=0;
				lblScore.setText("Score: "+score);
				started = false;
				c1=21;
				c2=21;
				btnStart.setEnabled(true);
				for(int i=0;i<20;i++)
				{
					fieldArray[i].setEnabled(false);
				}
				return true;
			}
		return false;
    }
 
	
	static void setListName(String listname, String username)
	{
		lblListName.setText("Current List: <" + listname + "> created by: <" + username+">");
	}
    
	
	
}
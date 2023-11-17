import javax.swing.JFrame;
import java.awt.Color;

// contains frame that stores MainPanel	
public class Main 
{
	// static instance, ONE mainPanel
	private static MainPanel mainPanel;
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Memory Vocabulary Trainer");	
		frame.setSize(776, 465);
		frame.setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.revalidate();
		frame.setLocationRelativeTo(null);
		
		mainPanel = new MainPanel();	
		frame.getContentPane().add(mainPanel);		
	}

	 // get static instance
	public static MainPanel getMainPanel()
	{
		return mainPanel;
	}

}


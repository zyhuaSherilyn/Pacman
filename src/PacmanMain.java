import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** The PacmanMain class
  * Plays a simple game of Pacman using the PacmanBoard class
  * @created by Sherilyn Hua
  * @created on December 25, 2013
  */

public class PacmanMain extends JFrame implements ActionListener
{
    //declares variables
    private JMenuItem newOption, highScoreOption, exitOption, rulesMenuItem, aboutMenuItem; //JMenu items
    private PacmanBoard gameBoard; //gameboard

    /**The PacmanMain method
      *Constructs a basic JFrame window for the game of Pacman
      */
    public PacmanMain ()
    {
	super ("Pacman"); //sets the title to "Pacman"
	setResizable (false); //not resizable

	setIconImage (Toolkit.getDefaultToolkit ().getImage ("pacmanIcon.png")); //sets the icon image

	gameBoard = new PacmanBoard (); //constructs a new gameboard
	getContentPane ().add (gameBoard, BorderLayout.CENTER); //adds the JComponents onto the ContentPane

	Dimension screen = Toolkit.getDefaultToolkit ().getScreenSize (); //gets the default screen size
	setLocation ((screen.width - gameBoard.BOARD_SIZE.width) / 2,
		(screen.height - gameBoard.BOARD_SIZE.height) - 100); //sets the location of the ContentPane

	//sets up the Game Menu
	JMenu gameMenu = new JMenu ("Game"); //creates a new JMenu called "Game"
	newOption = new JMenuItem ("New"); //creates a new JMenuItem called "New"
	newOption.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK)); //sets the key accelerator to Ctrl+'N'
	newOption.addActionListener (this); //adds an ActionListener to this JMenuItem
	exitOption = new JMenuItem ("Exit"); //creates a new JMenuItem called "Exit"
	exitOption.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK)); //sets the key accelerator to Ctrl+'X'
	exitOption.addActionListener (this); //adds an ActionListener to this JMenuItem
	highScoreOption = new JMenuItem ("High Score"); //creates a new JMenuItem called "High Score"
	highScoreOption.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, InputEvent.CTRL_MASK)); //sets the key accelerator to Ctrl+'H'
	highScoreOption.addActionListener (this); //adds an ActionListener to this JMenuItem

	// adds each MenuItem to the Game Menu (with a separator)
	gameMenu.add (newOption); //adds newOption
	gameMenu.addSeparator (); //adds a separator
	gameMenu.add (highScoreOption); //adds highScoreOption
	gameMenu.addSeparator (); //adds a separator
	gameMenu.add (exitOption); //adds exitOption

	// sets up the Help Menu
	JMenu helpMenu = new JMenu ("Help"); //creates a new JMenu called "Help"
	helpMenu.setMnemonic ('H'); //sets the mnemonic character to 'H'
	rulesMenuItem = new JMenuItem ("Rules...", 'R'); //creates a new JMenuItem called "Rules..."
	rulesMenuItem.addActionListener (this); //adds an ActionListener to this JMenuItem
	aboutMenuItem = new JMenuItem ("About...", 'A'); //creates a new JMenuItem called "About..."
	aboutMenuItem.addActionListener (this); //adds an ActionListener to this JMenuItem

	// adds each MenuItem to the Help Menu (with a separator)
	helpMenu.add (rulesMenuItem); //adds rulesMenuItem
	helpMenu.addSeparator (); //adds a separator
	helpMenu.add (aboutMenuItem); //adds aboutMenuItem

	// adds each Menu to the JMenuBar
	JMenuBar mainMenu = new JMenuBar (); //creates a new JMenuBar
	mainMenu.add (gameMenu); //adds the gameMenu
	mainMenu.add (helpMenu); //adds the helpMenu

	// sets the menu bar for this frame to mainMenu
	setJMenuBar (mainMenu);
    } //end of PacmanMain


    /**The actionPerformed method
      *Specifies the code to run when the user interacts with the JComponent
      */
    public void actionPerformed (ActionEvent event)
    {
	if (event.getSource () == newOption) //if the player selects "New"
	{
	    gameBoard.newGame (); //starts a new game
	}
	else if (event.getSource () == highScoreOption) //if the player selects "High Score"
	{
	    JOptionPane.showMessageDialog (this, "Score: 100", "High Score", JOptionPane.INFORMATION_MESSAGE);//displays high score information
	}
	else if (event.getSource () == exitOption) //if the player selects "Exit"
	{
	    hide (); //hides the window
	    System.exit (0); //exits
	}
	else if (event.getSource () == rulesMenuItem) //if the player selects "Rules..."
	{
	    JOptionPane.showMessageDialog (this, "Make PAC-MAN eat all dots to clear each stage!" +
		    "\nThere are three lives for PAC-MAN on each stage;" +
		    "\nhe loses one life every time a ghost catches him." +
		    "\n\n*Use arrow keys to control the movement of PAC-MAN" +
		    "\n**Every dot the Pacman eats represents 1 point" +
		    "\n***Clearing the game gives you 50 points at a time"+
		    "\n\nGood Luck!",
		    "Rules", JOptionPane.INFORMATION_MESSAGE); //displays the rules for the game in a separate window
	}
	else if (event.getSource () == aboutMenuItem) //if the player selects "About..."
	{
	    JOptionPane.showMessageDialog (this,
		    "by Sherry Hua" +
		    "\n@ 2013", "About Pacman",
		    JOptionPane.INFORMATION_MESSAGE); //displays the information of the game in a separate window
	}
    } //end of actionPerformed


    /**The main method
      *Starts up the game
      */
    public static void main (String[] args)
    {
	// starts up the PacmanMain frame
	PacmanMain frame = new PacmanMain (); //creates a new Pacman frame
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //sets default exit operation
	frame.pack ();
	frame.setVisible (true); //sets the JFrame to visible
    } // main method
} //end of class

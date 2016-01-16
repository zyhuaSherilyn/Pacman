import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The PacmanBoard class
  * Handles the board play for a simple game of Pacman
  * @created by Sherilyn Hua
  * @created on December 25, 2013
  */

public class PacmanBoard extends JPanel implements MouseListener, KeyListener
{
    //declaring constants
    private final int EMPTY = 0; //empty block
    private final int LEFT_UP_CORNER = 19; //top left corner
    private final int RIGHT_UP_CORNER = 22; //top right corner
    private final int RIGHT_DOWN_CORNER = 28; //bottom right corner
    private final int LEFT_DOWN_CORNER = 25; //bottom left corner
    private final int LEFT_BORDER = 1; //left border
    private final int TOP_BORDER = 2; //top border
    private final int RIGHT_BORDER = 4; //right border
    private final int BOTTOM_BORDER = 8; //bottom border
    private final int DOT = 16; //yellow dot

    private final Font SMALL_FONT = new Font ("Helvetica", Font.BOLD, 14); //font
    private final int SQUARE_SIZE = 24; //size of a block
    private final int NO_OF_ROWS = 15; //number of rows
    private final int NO_OF_COLUMNS = 15; //number of columns
    private final int NO_OF_BLOCKS = 15; //number of blocks
    private final int SCREEN_SIZE = NO_OF_BLOCKS * SQUARE_SIZE; //screen size
    private final boolean ANIMATION_ON = true; //sets animation on
    private final Color DOT_COLOR = new Color (192, 192, 0); //sets up the dots' color

    //artworks found in the same folder
    private final String IMAGE_FILENAME_PACMAN_RIGHT = "pacmanRight.png"; //pacman facing right
    private final String IMAGE_FILENAME_PACMAN_LEFT = "pacmanLeft.png"; //pacman facing left
    private final String IMAGE_FILENAME_PACMAN_UP = "pacmanUp.png"; //pacman facing up
    private final String IMAGE_FILENAME_PACMAN_DOWN = "pacmanDown.png"; //pacman facing down
    private final String IMAGE_FILENAME_GHOST = "ghost.png"; //ghost

    public final Dimension BOARD_SIZE = new Dimension (NO_OF_COLUMNS * SQUARE_SIZE + 1, NO_OF_ROWS * SQUARE_SIZE + 1); //sets up the size of the board

    //declaring program variables
    private int[] board = {19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
	21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
	17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
	17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
	25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
	1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
	1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
	1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
	1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
	1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
	1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
	1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
	9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28}; //sets up the board

    // declares program variables
    private int currentLives, score;
    private int currentColumn, currentRow;
    private Image pacFirstImage, pacSecondImage, pacThirdImage, pacFourthImage; //Pacman images
    private Image ghost; //ghost's image
    private boolean inGame, dying, gameOver;
    private Color mazeColor;

    /*
     * Constructs a new PacmanBoard object
    */
    public PacmanBoard ()
    {
	//sets up the board area, loads in piece images and starts a new game
	setPreferredSize (BOARD_SIZE);

	setBackground (new Color (0, 0, 0)); //sets background color to black

	// adds mouse listeners and key listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	requestFocusInWindow ();

	// loads up the images for the pieces
	pacFirstImage = new ImageIcon (IMAGE_FILENAME_PACMAN_RIGHT).getImage (); //Pacman facing right
	pacSecondImage = new ImageIcon (IMAGE_FILENAME_PACMAN_LEFT).getImage (); //Pacman facing left
	pacThirdImage = new ImageIcon (IMAGE_FILENAME_PACMAN_UP).getImage (); //Pacman facing up
	pacFourthImage = new ImageIcon (IMAGE_FILENAME_PACMAN_DOWN).getImage (); //Pacman facing down
	ghost = new ImageIcon (IMAGE_FILENAME_GHOST).getImage (); //ghost

	newGame (); //starts a new game
    } //end of PacmanBoard


    /**The newGame method
      *Starts a new game
      */
    public void newGame ()
    {
	score = 0; //sets the score to zero at the beginning of the game
	currentLives = 3; //the player is given three lives when starting the game
	dying = false; //sets dying to false
	gameOver = false; //sets gameOver to false
	currentRow = 0; //sets currentRow to zero
	currentColumn = 0; //sets currentColumn to zero
	repaint (); //repaints the screen after the change

    } //end of newGame


    public void playGame (Graphics2D g2d)
    {
	if (dying)
	{
	    Death ();
	}
	else
	{
	    movePacMan ();
	    drawPacMan (g2d);
	    moveGhosts (g2d);
	    checkMaze ();
	}
    }


    public void ShowIntroScreen (Graphics2D g2d)
    {
	String s = "Press s to start.";
	Font small = new Font ("Helvetica", Font.BOLD, 14);
	FontMetrics metr = this.getFontMetrics (small);

	g2d.setColor (Color.white);
	g2d.setFont (small);
	g2d.drawString (s, (SCREEN_SIZE - metr.stringWidth (s)) / 2, SCREEN_SIZE / 2);
    }


    public void drawScore (Graphics2D g)
    {
	g.setFont (SMALL_FONT);
	g.setColor (new Color (96, 128, 255));
	String s = "Score: " + score;
	g.drawString (s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
	for (int i = 0 ; i < currentLives ; i++)
	{
	    g.drawImage (pacFirstImage, i * 28 + 8, SCREEN_SIZE + 1, this);
	}
    }


    public void checkMaze ()
    {
	short i = 0;
	boolean finished = true;

	while (i < NO_OF_BLOCKS * NO_OF_BLOCKS && finished)
	{
	    if (board [i] == 16)
		finished = false;
	    i++;
	}

	if (finished)
	{
	    score += 50;
	}
    }


    public void Death ()
    {
	currentLives--;
	if (currentLives == 0)
	    ingame = false;
    }


    public void moveGhosts (Graphics2D g2d)
    {
	int pos;
	int count;

	for (int i = 0 ; i < nrofghosts ; i++)
	{
	    if (ghostx [i] % SQUARE_SIZE == 0 && ghosty [i] % SQUARE_SIZE == 0)
	    {
		pos = ghostx [i] / SQUARE_SIZE + nrofblocks * (ghosty [i] / SQUARE_SIZE);

		count = 0;
		if ((board [pos] & 1) == 0 && ghostdx [i] != 1)
		{
		    dx [count] = -1;
		    dy [count] = 0;
		    count++;
		}
		if ((board [pos] & 2) == 0 && ghostdy [i] != 1)
		{
		    dx [count] = 0;
		    dy [count] = -1;
		    count++;
		}
		if ((board [pos] & 4) == 0 && ghostdx [i] != -1)
		{
		    dx [count] = 1;
		    dy [count] = 0;
		    count++;
		}
		if ((board [pos] & 8) == 0 && ghostdy [i] != -1)
		{
		    dx [count] = 0;
		    dy [count] = 1;
		    count++;
		}

		if (count == 0)
		{
		    if ((board [pos] & 15) == 15)
		    {
			ghostdx [i] = 0;
			ghostdy [i] = 0;
		    }
		    else
		    {
			ghostdx [i] = -ghostdx [i];
			ghostdy [i] = -ghostdy [i];
		    }
		}
		else
		{
		    count = (int) (Math.random () * count);
		    if (count > 3)
			count = 3;
		    ghostdx [i] = dx [count];
		    ghostdy [i] = dy [count];
		}

	    }
	    ghostx [i] = ghostx [i] + (ghostdx [i] * ghostspeed [i]);
	    ghosty [i] = ghosty [i] + (ghostdy [i] * ghostspeed [i]);
	    DrawGhost (g2d, ghostx [i] + 1, ghosty [i] + 1);

	    if (pacmanx > (ghostx [i] - 12) && pacmanx < (ghostx [i] + 12) &&
		    pacmany > (ghosty [i] - 12) && pacmany < (ghosty [i] + 12) &&
		    ingame)
	    {

		dying = true;
		deathcounter = 64;

	    }
	}
    }


    // Keyboard events you can listen for since this JPanel is a KeyListener

    /**Responds to a keyPressed event
      *@param event information about the key pressed event
      */
    public void keyPressed (KeyEvent event)
    {
	// Change the currentRow and currentColumn of the player based on the key pressed
	if (event.getKeyCode () == KeyEvent.VK_LEFT) //if the player presses the left-arrow key
	{
	    if (currentColumn > 0) //if currentColumn is greater than 1
		currentColumn--; //subtracts 1 from currentColumn
	}


	else if (event.getKeyCode () == KeyEvent.VK_RIGHT) //if the player presses the right-arrow key
	{
	    if (currentColumn < NO_OF_COLUMNS - 1) //if currentColumn is less than max column
		currentColumn++; //adds 1 to currentColumn
	}


	else if (event.getKeyCode () == KeyEvent.VK_DOWN) //if the player presses the down-arrow key
	{
	    if (currentRow < NO_OF_ROWS - 1) //if currentRow is less than max row
		currentRow++; //adds 1 to currentRow
	}


	else if (event.getKeyCode () == KeyEvent.VK_UP) //if the player presses the up-arrow key
	{
	    if (currentRow > 0) //if currentRow is greater than 1
		currentRow--; //subtracts 1 from currentRow
	}


	repaint (); //repaints the screen after the change
    } //end of keyPressed


    /**The keyReleased method
      */
    public void keyReleased (KeyEvent event)
    {
    }


    /**The keyTyped method
      */
    public void keyTyped (KeyEvent event)
    {
    }


    /**The mousePressed method
      */
    public void mousePressed (MouseEvent event)
    {
    }


    /**The mouseReleased method
      */
    public void mouseReleased (MouseEvent event)
    {
    }


    /**The mouseClicked method
      */
    public void mouseClicked (MouseEvent event)
    {
    }


    /**The mouseEntered method
      */
    public void mouseEntered (MouseEvent event)
    {
    }


    /**The mouseExited method
      */
    public void mouseExited (MouseEvent event)
    {
    }
}

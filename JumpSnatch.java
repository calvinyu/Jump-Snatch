package javagame;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.media.jai.codec.PNGEncodeParam.RGB;



public class JumpSnatch extends Applet implements Runnable{

	//Players
	private RandomPlayer rp1;
	private SmartPlayer sp;
	
	private Image i;
	private Graphics doubleG;
	private Board2 b;
	
	private Kernel k;
	private Label nameLabel;
	private TextField nameTextField;
	private MyRadioButton playerOneRadio;
	private String[] playerOneChoice = {"#Player1: ", "User1", "Dumb Computer", "Smart Computer"};
	private MyRadioButton playerTwoRadio;
	private ResetButton resetButton;
	private String[] playerTwoChoice = {"#Player2: ", "User2",  "Dumb Computer", "Smart Computer"};
	private StartButton startButton;
	private List optionListx;
	private List optionListy;
	private Label userMessage;
	private int delay;
	//test mouse
	private MouseTest me;
	private ColorChooser cc;
	//private MouseEvent me;
	private boolean gameOver = false;
	private boolean startOver = false;
	private int x;
	private int y;
	private int dim_x;
	private int dim_y;
	private int winner;
	private boolean player1 = false;
	private boolean player2 = false;
	@Override
	public void init() {

		//gui.setBackground(Color.orange);
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		setLayout(layout);
		setBackground(Color.pink);
		//default dimension
		dim_x = 3;
		dim_y = 3;
		delay  = 500;
		winner = -1;
		b = new Board2(dim_x, dim_y, Color.blue);
		// TODO Auto-generated method stub
		nameLabel = new Label("  Enter your name");
		nameTextField = new TextField("Barack Obama");
		playerOneRadio = new MyRadioButton(playerOneChoice);
		playerTwoRadio = new MyRadioButton(playerTwoChoice);
		startButton = new StartButton(" Start ", this);
		resetButton = new ResetButton(this);
		String dimChoice[] = {"3", "4", "5", "6", "7"};
		optionListx = new List(dimChoice, "Please choose Game Size: ");
		optionListy = new List(dimChoice, "X");
		userMessage = new Label("    Welcome!!Enjoy the game!!");
		userMessage.setForeground(Color.CYAN);
		userMessage.setFont(new Font("Serif", Font.BOLD, 25));
		userMessage.setPreferredSize(new Dimension(500, 25));
		cc = new ColorChooser();
		//test mouse 
		me = new MouseTest();

		add(cc);
		add(optionListx);
		add(optionListy);
		add(nameLabel);
		add(nameTextField);
		add(playerOneRadio);
		add(resetButton);
		add(playerTwoRadio);
		add(startButton);
		add(userMessage);
		addMouseListener(me);
		addMouseMotionListener(me);
		//Handler handler = new Handler();
		//addMouseListener(handler);
		//addMouseMotionListener(handler);
		setSize(550, 650);
		this.setVisible(true);
	}
	
	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		rp1 = new RandomPlayer(dim_x, dim_y);
		sp = new SmartPlayer();
		while(true){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(startButton.isClicked()){
				resetButton.setClicked(false);
				setEnable(false);
				reset();
				newgame();
				setEnable(true);
				startButton.setClicked(false);
			}
		}
	}
	public String getWinner(){
		if(nameTextField.getText().equals("Barack Obama"))
			return "not set";
		else
			return nameTextField.getText();
		
		
	}
	public String getWinnerScore(){
		if(winner == 1 && player1 && !player2 || winner == 2 && !player1 && player2){
			return "WIN";
		}
		else if(winner == 2 && player1 && !player2 || winner == 1 && !player1 && player2){
			return "LOSE";
		}
		else{
			return "0";
		}
		
	}
	public void setEnable(boolean bool){
		playerOneRadio.setEnabled(bool);
		playerTwoRadio.setEnabled(bool);
		optionListx.setEnabled(bool);
		optionListy.setEnabled(bool);
	}
	public void reset(){
		userMessage.setText("    Resetting");
		dim_x = optionListx.getValue();
		dim_y = optionListy.getValue();
		rp1.reset(dim_x, dim_y);
		k = new Kernel(dim_x, dim_y);
		b = new Board2(dim_x, dim_y, cc.getColor());
		b.update(k);
		startButton.setEnabled(true);
		//resetButton.setClicked(false);
		gameOver =false;
		repaint();
		startButton.setText("ReStart");
		userMessage.setText("    Reset Completed");
	}
	public void newgame(){
		Coordinate start = new Coordinate(0,0);
		Coordinate end = new Coordinate(0,0);
		int turn =0;
		userMessage.setText("    Game Started");
		if(playerOneRadio.getSvalue().equals("User1")) player1 = true;
		else player1 = false;
		if(playerTwoRadio.getSvalue().equals("User2")) player2 = true;
		else player2 = false;
		while(!gameOver){
			if (resetButton.isClicked()) break;
			//System.out.println(playerOneRadio.getSvalue());
			String playerOneSelected = playerOneRadio.getSvalue();
			String playerTwoSelected = playerTwoRadio.getSvalue();
			if(turn == 0 ) showStatus("Waiting for Player one: " + playerOneSelected);
			if(turn == 1 ) showStatus("Waiting for Player two: " + playerTwoSelected);
			
			if( turn ==0 && playerOneSelected.equalsIgnoreCase("Dumb Computer") ||
				turn ==1 && playerTwoSelected.equalsIgnoreCase("Dumb Computer") ){
				rp1.get_move(k, start, end);
			}else if( turn ==0 && playerOneSelected.equalsIgnoreCase("Smart Computer") ||
					  turn ==1 && playerTwoSelected.equalsIgnoreCase("Smart Computer") ){
				sp.get_move(k, start, end);
				}
			else if(turn == 0 && playerOneSelected.equalsIgnoreCase("User1") ||
					turn == 1 && playerTwoSelected.equalsIgnoreCase("User2") ){
				me.setMode(1);
				do{
					try {Thread.sleep(20);}
					catch (InterruptedException e) {e.printStackTrace();}
					
				}while(me.getMode() != 3);
				start = b.transfer(me.getStart());
				end = b.transfer(me.getEnd());
				System.out.println(start + " To " + end);
			}
			//if(b.checkIndex(start) && b.checkIndex(end) && k.move(turn, start, end)){
			if(k.move(turn, start, end)){
				turn = 1 - turn;
			}
			if(!k.getMessage().equals(""))
				userMessage.setText("    " + k.getMessage());
			//userMessage.setText("    Test");
			b.update(k);
			repaint();
			gameOver = b.getGameOver();
			
			//Slow down the game, so the user can see
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		winner = b.getWinner();
		System.out.println(getWinner());
	}
	@Override
	public void update(Graphics g) {
		if(i == null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		g.drawImage(i, 0, 0, this);
	}
	
	@Override
	public void paint(Graphics g) {
		b.paint(g);
	}
}

package javagame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Board2{
	private int[][] s = new int[30][30];
	private int xn = 3;
	private int yn = 3;
	private int xoffset = 20;
	private int yoffset = 200;
	private int gridsize = 100;
	private int pawnsize= 80;
	private boolean gameOver = false;
	private int winner;
	private int score;
	private Color color;
	public Board2(int xn, int yn, int[][] s){
		this.s=s;
		this.xn = xn;
		this.yn = yn;
	}
	
	public int[][] getS() {
		return s;
	}
	public int getXn() {
		return xn;
	}
	public int getYn() {
		return yn;
	}
	
	public Board2(int xn, int yn, Color color) {
		// TODO Auto-generated constructor stub
		this.xn = xn;
		this.yn = yn;
		this.color = color;
		gridsize = 400/Math.max(xn, yn);
		pawnsize = (int) (gridsize * 0.8);
		for(int i=0; i<xn; ++i){
			for(int j=0; j<yn; ++j){
				s[i][j] = 1;
			}
		}
		
	}
	public void paint(Graphics g){
		//setsize
		
		//Fill the grid
		for(int i=0; i<xn; ++i){
			for(int j=0; j<yn; ++j){
				if(s[i][j]==1){
					g.setColor(color);
				}
				else{
					g.setColor(Color.white);
				}
				g.fillOval(xoffset + i*gridsize+(gridsize - pawnsize)/2, yoffset + j*gridsize+(gridsize - pawnsize)/2, pawnsize, pawnsize);
				
			}
		}
		
		//Draw horizontal lines
		g.setColor(Color.black);
		//drawing  horizontal lines
		for(int i=0; i <= yn; ++i){
			g.drawLine(xoffset, yoffset + i * gridsize, xoffset + xn * gridsize, yoffset + i * gridsize );
		}
		
		//drawing vertical lines
		for(int i=0; i <= xn; ++i){
			g.drawLine(xoffset + i *gridsize , yoffset, xoffset + i * gridsize, yoffset +  yn* gridsize );
		}
		//GAME OVER
		if(gameOver){
			printGameOver(g);
		}
	}
	
	public Coordinate transfer(Coordinate x){
		x.x-=xoffset;
		x.y-=yoffset;
		x.x/=gridsize;
		x.y/=gridsize;
		return x;
	}
	
	private void printGameOver(Graphics g){
		Font f = new Font("Serif", Font.ITALIC + Font.BOLD, 40);
		g.setColor(Color.red);
		g.setFont(f);
		g.drawString("Game Over", 120, 330);
		g.setColor(Color.magenta);
		g.drawString(String.format("Winner is Player %d" , winner), 110, 370);
		g.drawString(String.format("Score is %s", score), 130, 410);
	}
	public boolean checkIndex(Coordinate c){
		if(c.x < 0 || c.x >= xn || c.y <0 || c.y >=yn) return false;
		return true;
	}
	public void update(JumpSnatch startingPoint) {
		// TODO Auto-generated method stub
		
	}
	public void update(Kernel k) {
		// TODO Auto-generated method stub
		score = k.getScore();
		s = k.getS();
		if(k.count() == 1 || k.getWidth() <= 2 && k.getHeight()<=2){
			winner = k.getPlayer()+1;
			gameOver = true;
		}
		else{
			gameOver = false;
		}
		//System.out.println("count is " + k.count());
	}
	
	public boolean getGameOver() {
		// TODO Auto-generated method stub
		return gameOver;
	}
	public int getWinner() {
		return winner;
	}
}

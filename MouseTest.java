package javagame;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class MouseTest extends Panel implements MouseListener, MouseMotionListener{
	private int x;
	private int y;
	private int mode = 0;
	private Coordinate start, end;
	public MouseTest(){
	}	
	public MouseTest(int width, int height){
		
	}
	public boolean move(Kernel k, Coordinate start, Coordinate end){
		
		
		
		return true;
	}
	
	public void init(){
		setBackground(Color.pink);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void paint(Graphics g){
		g.drawString("Mouse is at " + x  + ",  "  + y, x, y);
		System.out.println(x + "," + y);
		//showStatus(x + "," + y);
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.printf("You Dragged %d, %d\n", e.getX(), e.getY());
	}


	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.printf("You Moved %d, %d\n", e.getX(), e.getY());	
	}


	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		//System.out.printf("You Clicked %d, %d\n", e.getX(), e.getY());
	}


	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(mode == 1){
			start = new Coordinate(e.getX(), e.getY());
			mode = 2;
			//System.out.printf("You Pressed %d, %d\n", e.getX(), e.getY());
		}
	}


	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(mode == 2){
			mode =3;
			end = new Coordinate(e.getX(), e.getY());
			System.out.printf("%s to %s\n", start, end);
		}
		else{
			mode = 1;
		}
		//System.out.printf("You Released %d, %d\n", e.getX(), e.getY());
	}


	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.printf("You Entered %d, %d\n", e.getX(), e.getY());
	}


	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

		//statusbar.setText("Exited");
		//mousepanel.setBackground(Color.yellow);
	}
	public int getXX(){
		return x;
	}
	public int getYY(){
		return y;
	}
	
	public Coordinate getCoor(){
		return new Coordinate(x,y);
	}
	public int getMode() {
		return mode;
		}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Coordinate getStart(){
		return start;
	}
	public Coordinate getEnd(){
		return end;
	}
}

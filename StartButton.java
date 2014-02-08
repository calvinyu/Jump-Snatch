package javagame;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class StartButton extends Panel{
	private JButton mybutton;
	private boolean clicked = false;
	private JumpSnatch sp;
	public StartButton(String s, JumpSnatch sp){
		this.sp = sp;
		mybutton = new JButton(s);
		MyListener mylistener = new MyListener();
		mybutton.addActionListener(mylistener);
		add(mybutton);
		
	}
	public void start(JumpSnatch sp){
		//sp.run();
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		if(clicked == false) mybutton.setEnabled(true);
		this.clicked = clicked;
	}
	public class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setClicked(true);
			mybutton.setEnabled(false);
		}
		
	}
	public void setText(String s){
		mybutton.setText(s);
	}
}

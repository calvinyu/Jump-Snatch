package javagame;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ResetButton extends Panel{
	private JButton mybutton;
	private boolean clicked;
	private JumpSnatch st;
	public ResetButton(JumpSnatch st){
		this.st = st;
		mybutton = new JButton("Reset");
		mybutton.addActionListener(new Mylistener());
		add(mybutton);
	}
	class Mylistener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setClicked(true);
			st.reset();
			//setClicked(false);
		}
		
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
}


package javagame;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;


public class MyRadioButton extends Panel{
	private JLabel tf;
	private ButtonGroup group;
	private JRadioButton[] jb = new JRadioButton[10];
	private JButton startButton;
	private JList mylist;
	private int value;
	private String svalue;
	public MyRadioButton(String[] s){
		tf = new JLabel(s[0]);
		tf.setSize(150, 20);
		group = new ButtonGroup();
		//startButton = new JButton("Start");
		mylist = new JList(s);
		add(tf);
		
		for(int i=1; i<s.length; ++i){
			jb[i] = new JRadioButton(s[i]);
			add(jb[i]);
			group.add(jb[i]);
			jb[i].addItemListener(new HandlerClass(s[i], i));
		}
		jb[2].setSelected(true);
	}
	
	private class HandlerClass implements ItemListener{
		private int index;
		private String s;
		//the font object get variable font
		public HandlerClass(String s, int index){
			this.index = index;
			this.s = s;
		}
		
		public void itemStateChanged(ItemEvent event){
			Font myfont = new Font("Serif", Font.ITALIC+ Font.BOLD, 20);
			//tf.setBackground(Color.blue);
			//tf.setFont(myfont);
			//setText(s);
			svalue = s;
			value = index;
		}
	}
	private void setText(String s){
		tf.setText(s);
	}
	public int getValue() {
		return value;
	}
	public String getSvalue() {
		return svalue;
	}
}

package javagame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class List extends Panel{
	
	private int value;
	private JLabel mylabel;
	private JList mylist;
	private JMenu mymenu;
	private JComboBox mybox;
	private String[] s;
	
	public List(String[] s, String text){
		this.s = s;
		mylabel = new JLabel(text);
		mymenu = new JMenu();
		mybox = new JComboBox(s);
		mybox.addActionListener(
				new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				value = mybox.getSelectedIndex();
			}});
		mylist = new JList(s);
		mylist.setVisibleRowCount(s.length);
		mylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//mylist.setVisible(true);
		
		mylist.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						value = mylist.getSelectedIndex();
					}
				}
				);
		add(mylabel);
		add(mybox);
		mybox.setSelectedIndex(0);
		//add(new JScrollPane(mylist));
		//mylist.setSelectedIndex(3);
	}
	public int getValue() {
		return Integer.parseInt(s[value]);
	}

}

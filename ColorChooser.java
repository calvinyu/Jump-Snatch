package javagame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ColorChooser extends Panel{

	private JButton b;
	private Color color = Color.blue;
	private JPanel panel;
	
	public ColorChooser(){
		b = new JButton("Pick a color");
		b.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						color = JColorChooser.showDialog(null, "Pick your color", color);
						if(color == null){
							color = Color.blue;
						}
						
						panel.setBackground(color);
					}
					
				}
				);
		add(b);
	}
	public Color getColor(){
		return color;
	}
	
}

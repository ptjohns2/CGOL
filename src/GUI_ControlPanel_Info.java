import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;


public class GUI_ControlPanel_Info extends JPanel{
	JTextArea textArea;
	String text;
	JScrollPane scrollPane;
	Dimension dim;
	
	public GUI_ControlPanel_Info(){
		text = 	"   =Rules of Conway's Game of Life=\n" +
				"   -For each living cell, if it has _____ living neighbors, it _____ :\n" + 
				"        < 2        dies\n" +
				"        2 or 3    lives\n" +
				"        > 3        dies\n" +
				"   -For each dead cell, if it has _____ living neighbors, it _____ :\n" +
				"        3           regenerates" 
				;
		
		textArea = new JTextArea(text);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		dim = new Dimension(600, 120);
		scrollPane.setPreferredSize(dim);
		add(scrollPane);
	}
}

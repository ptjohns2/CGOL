import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class GUI_ControlPanel_Basic extends JPanel{
	GUI_SimGrid sim;
	
	JPanel middle, right;
	
	//MIDDLE
	JButton button_start,
			button_stop,
			button_clear_board;
	
	//RIGHT
	JButton button_skip_to_generation,
			button_next_generation;
	
	JTextField textfield_generation_number;
	
	
	public GUI_ControlPanel_Basic(GUI_SimGrid sim)
	{		
		this.sim = sim;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			//MIDDLE
			middle = new JPanel();
			middle.setBorder(BorderFactory.createLineBorder(Color.black));
			middle.setLayout(new GridLayout(3, 1));
				button_start = new JButton("Start simulation");
				button_start.addActionListener(new listener_button_start());
			middle.add(button_start);
				button_stop = new JButton("Stop simulation");
				button_stop.addActionListener(new listener_button_stop());
			middle.add(button_stop);
				button_clear_board = new JButton("Clear board");
				button_clear_board.addActionListener(new listener_button_clear_board());
			middle.add(button_clear_board);
		this.add(middle);	
		
			//RIGHT
			right = new JPanel();
			right.setBorder(BorderFactory.createLineBorder(Color.black));
			right.setLayout(new GridLayout(3, 1));
				button_skip_to_generation = new JButton("Skip to generation :");
				button_skip_to_generation.addActionListener(new listener_button_skip_to_generation());
			right.add(button_skip_to_generation);
				textfield_generation_number = new JTextField("");
			right.add(textfield_generation_number);
				button_next_generation = new JButton("Next generation");
				button_next_generation.addActionListener(new listener_button_next_generation());
			right.add(button_next_generation);
		this.add(right);
		
	}
	
	public class listener_button_next_generation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			sim.nextGen();
		}
	}
	public class listener_button_skip_to_generation implements ActionListener{
		public void actionPerformed(ActionEvent e){
			button_skip_to_generation.setBackground(null);
			int gen_to_skip_to = 0;
			boolean chk = sim.is_running();
			buttons_disable();
			sim.stop();
			try{
				gen_to_skip_to = Integer.parseInt(textfield_generation_number.getText());
			}catch(NumberFormatException nfe){
				buttons_enable();
				button_skip_to_generation.setBackground(Color.RED);
				button_start.setBackground(null);
				sim.stop();
				return;
			}
			sim.skip_to_gen(gen_to_skip_to);
			if(chk){sim.start();}
			buttons_enable();
		}
	}
	public class listener_button_clear_board implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sim.clear_board();
			sim.clear_board();
		}
	}
	public class listener_button_start implements ActionListener{
		public void actionPerformed(ActionEvent e){
			button_start.setBackground(Color.cyan);
			sim.start();
		}
	}
	public class listener_button_stop implements ActionListener{
		public void actionPerformed(ActionEvent e){
			button_start.setBackground(null);
			sim.stop();
		}
	}
	
	public void buttons_disable(){
		button_start.setEnabled(false);
		button_stop.setEnabled(false);
		button_clear_board.setEnabled(false);

		button_skip_to_generation.setEnabled(false);
		button_next_generation.setEnabled(false);
	}
	
	public void buttons_enable(){
		button_start.setEnabled(true);
		button_stop.setEnabled(true);
		button_clear_board.setEnabled(true);

		button_skip_to_generation.setEnabled(true);
		button_next_generation.setEnabled(true);
	}

}

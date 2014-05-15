import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class GUI_ControlPanel_Advanced extends JPanel{
	GUI_SimGrid sim;
	JPanel panel;
	
		JPanel panel_sub_mouse_action;
			JLabel label_sub_mouse_action;
			ButtonGroup bg_mouse_action;
				JRadioButton rb_flip;
				JRadioButton rb_kill;
				JRadioButton rb_grow;
				
		JPanel panel_sub_trail;
			JCheckBox cb_trail;
			
		JPanel panel_sub_color;
			Vector arr_colors;
			JComboBox combobox_set_color_alive;
			JComboBox combobox_set_color_dead;
			
		JPanel panel_sub_randomize;
			JButton button_randomize;
			
			
	public GUI_ControlPanel_Advanced(GUI_SimGrid sim){
		this.sim = sim;
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
			panel_sub_mouse_action = new JPanel();
			panel_sub_mouse_action.setLayout(new BoxLayout(panel_sub_mouse_action, BoxLayout.X_AXIS));
				label_sub_mouse_action = new JLabel("Mouse action :");
				bg_mouse_action = new ButtonGroup();
					rb_flip = new JRadioButton("Flip");
					rb_flip.setActionCommand("f");
					rb_flip.addActionListener(new bg_mouse_action_listener());
					rb_kill = new JRadioButton("Kill");
					rb_kill.setActionCommand("k");
					rb_kill.addActionListener(new bg_mouse_action_listener());
					rb_grow = new JRadioButton("Grow");
					rb_grow.setActionCommand("g");
					rb_grow.addActionListener(new bg_mouse_action_listener());
				bg_mouse_action.add(rb_flip);
				bg_mouse_action.add(rb_kill);
				bg_mouse_action.add(rb_grow);
			panel_sub_mouse_action.add(label_sub_mouse_action);
			panel_sub_mouse_action.add(rb_flip);
			panel_sub_mouse_action.add(rb_kill);
			panel_sub_mouse_action.add(rb_grow);
		panel.add(panel_sub_mouse_action);
		
//		//				
//		JPanel panel_sub_trail;
//		JPanel label_sub_trail;
//			JRadioButton rb_trail;
		
			panel_sub_trail = new JPanel();
					cb_trail = new JCheckBox("Show corpse trail?");
					cb_trail.addActionListener(new cb_trail_listener());
			panel_sub_trail.add(cb_trail);
		panel.add(panel_sub_trail);
		
		
			panel_sub_color = new JPanel();
			panel_sub_color.setLayout(new BoxLayout(panel_sub_color, BoxLayout.X_AXIS));
				arr_colors = new Vector();

				arr_colors.add("");
				arr_colors.add("black");
				arr_colors.add("white");
				arr_colors.add("red");
				arr_colors.add("pink");
				arr_colors.add("magenta");
				arr_colors.add("blue");
				arr_colors.add("cyan");
				arr_colors.add("green");
				arr_colors.add("yellow");
				arr_colors.add("orange");
				
				combobox_set_color_dead = new JComboBox(arr_colors);
				combobox_set_color_dead.addActionListener(new cb_color_listener());
				combobox_set_color_alive = new JComboBox(arr_colors);
				combobox_set_color_alive.addActionListener(new cb_color_listener());
			panel_sub_color.add(new JLabel("Colors :     Alive = "));
			panel_sub_color.add(combobox_set_color_alive);
			panel_sub_color.add(new JLabel("     Dead = "));
			panel_sub_color.add(combobox_set_color_dead);
		panel.add(panel_sub_color);
		
	/*		JPanel panel_sub_randomize;
			JButton button_randomize;*/
		panel_sub_randomize = new JPanel();
			button_randomize = new JButton("Randomize");
			button_randomize.addActionListener(new button_randomize_listener());
			panel_sub_randomize.add(button_randomize);
		panel.add(panel_sub_randomize);
		
		this.add(panel);
	}
	
	
	public class bg_mouse_action_listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command_str = e.getActionCommand();
			char command_char = command_str.charAt(0);
			System.out.println("mouse action changed to " + command_char);
			sim.set_mouse_action(command_char);
		}
	}

	public class cb_color_listener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color tmp_color = Color.black;
			boolean alive_or_dead = true;
			String str_color = "black";
			
			JComboBox combobox = (JComboBox)e.getSource();
			str_color = (String)combobox.getSelectedItem();
			
			if(combobox == combobox_set_color_alive){
				alive_or_dead = true;
			}else if(combobox == combobox_set_color_dead){
				alive_or_dead = false;
			}
			
			if(str_color.equals("black")){
				tmp_color = Color.black;
			}else if(str_color.equals("white")){
				tmp_color = Color.white;
			}else if(str_color.equals("pink")){
				tmp_color = Color.pink;
			}else if(str_color.equals("red")){
				tmp_color = Color.red;
			}else if(str_color.equals("magenta")){
				tmp_color = Color.magenta;
			}else if(str_color.equals("blue")){
				tmp_color = Color.blue;
			}else if(str_color.equals("cyan")){
				tmp_color = Color.cyan;
			}else if(str_color.equals("green")){
				tmp_color = Color.green;
			}else if(str_color.equals("yellow")){
				tmp_color = Color.yellow;
			}else if(str_color.equals("orange")){
				tmp_color = Color.orange;
			}else{
				return;
			}
			sim.setColor(tmp_color, alive_or_dead);	
			System.out.println("ActionEvent combobox: color = " + str_color + "\t" + tmp_color);
		}
	}
	public class cb_trail_listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			boolean chk = cb_trail.isSelected();
			if(chk == true){
				System.out.println("\nGUI push TRAILS ON");
				sim.setTrailsOn();
			}else{
				System.out.println("\nGUI push TRAILS OFF");
				sim.setTrailsOff();
			}
		}
		
	}
	public class button_randomize_listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			sim.setRandom();
		}
	}

}

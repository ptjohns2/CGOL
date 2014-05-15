///////////
///////////
//////////

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class CGOL extends JFrame{
	public void init(){
		int board_cell_num_x = 100;
		int board_cell_num_y = 100;
		int dim_x = 700;
		int dim_y = 600;

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		GUI_SimGrid sim = new GUI_SimGrid(board_cell_num_x, board_cell_num_y, dim_x, dim_y);
		panel.add(sim);
		GUI_ControlPanel cp = new GUI_ControlPanel(sim);
		//cp.setFocusCycleRoot(true);
		panel.add(new GUI_ControlPanel(sim));
		sim.getControlPanel(cp);
		
		this.add(panel);
		setSize(dim_x,dim_y);
		
	}
}

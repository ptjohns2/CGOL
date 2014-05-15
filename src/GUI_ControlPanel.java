import java.awt.*;
import javax.swing.*;


public class GUI_ControlPanel extends JPanel{
	GUI_SimGrid sim;
	
	JTabbedPane tabbedPane;
		GUI_ControlPanel_Basic 		tab_1_Basic;
		GUI_ControlPanel_Advanced 	tab_2_Advanced;
		GUI_ControlPanel_Info		tab_3_Info;
	
	
	public GUI_ControlPanel(GUI_SimGrid sim){
		this.sim = sim;
		tabbedPane = new JTabbedPane();
		
		tab_1_Basic = new GUI_ControlPanel_Basic(sim);
		tab_2_Advanced = new GUI_ControlPanel_Advanced(sim);
		tab_3_Info = new GUI_ControlPanel_Info();
		
		tabbedPane.addTab("Basic", tab_1_Basic);
		tabbedPane.addTab("Advanced", tab_2_Advanced);
		tabbedPane.add("===Conway's Game of Life===", tab_3_Info);
		tabbedPane.setPreferredSize(new Dimension(600, 150));
		this.add(tabbedPane);
	}
	
	

}

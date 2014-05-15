import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.util.*;



public class GUI_SimGrid extends JPanel{
	boolean is_on;
	char mouse_action;
	thread_sim_run threadController;
	java.util.Timer timer;
	int simSpeed_ms;
	
	GUI_ControlPanel cp;
	private JPanel panel;
	private int size_x;
	private int size_y;
	private Cell[][] sim_grid;
	
	Color color_true = Color.black;
	Color color_false = Color.white;
	Color color_corpse = Color.lightGray;
	boolean trails;
	
	boolean mouseState;
	short pointerAction;
	Brain brain;

	public GUI_SimGrid(int arg_num_x, int arg_num_y, int dim_x, int dim_y){
		is_on = false;
		trails = false;
		mouse_action = 'f';
		simSpeed_ms = 100;
		threadController = new thread_sim_run();
		timer = new java.util.Timer();
		refreshCorpseColor();
		
		size_x = arg_num_x;
		size_y = arg_num_y;
		sim_grid = new Cell[size_x][size_y];
		brain = new Brain(this);
		
		panel = new JPanel();
		
		panel.setLayout(new GridLayout(size_y, size_x));
		for(int i=0; i<size_x; i++){
			for(int j=0; j<size_y; j++){
				System.out.println("Cell constructed:\t(" + i + ", " + j + ")");
				sim_grid[i][j] = new Cell(false);
				sim_grid[i][j].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				sim_grid[i][j].addMouseListener(new CellListener_flip());
			}
		}
		
		for(int j=0; j<size_y; j++){
			for(int i=0; i<size_x; i++){
				panel.add(sim_grid[i][j]);
			}
		}


		JScrollPane scrollpane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,   
		        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setPreferredSize(new Dimension(dim_x, dim_y-165));
		
		//TEST - rewrite
		int vertical_min = scrollpane.getVerticalScrollBar().getMinimum();
		int vertical_max = scrollpane.getVerticalScrollBar().getMaximum();
		int horizontal_min = scrollpane.getHorizontalScrollBar().getMinimum();
		int horizontal_max = scrollpane.getHorizontalScrollBar().getMaximum();
		int vertical_init = (vertical_min + vertical_max) / 2;
		int horizontal_init = (horizontal_min + horizontal_max) / 2;
		scrollpane.getVerticalScrollBar().setValue(vertical_init);
		scrollpane.getHorizontalScrollBar().setValue(horizontal_init);
		//end rewrite
		
		this.add(scrollpane);
		
		
	}
	
	
	public class Cell extends JPanel{
		boolean hasCorpse;
		boolean state;
		boolean state_prev;
		
		public Cell(boolean state){
			if(state == true){
				this.setBackground(color_true);
			}else{
				this.setBackground(color_false);
			}
			this.state = state;
		}

		public void setState(Boolean state){
			state_prev = this.state;
			this.state = state;
			if(state == true){
				this.setBackground(color_true);
			}else{
				if(state_prev == true){
					hasCorpse = true;
				}
				if(hasCorpse == true){
					this.setBackground(color_corpse);
				}else{
					this.setBackground(color_false);
				}
			}
		}
		public void flipState(){
			state_prev = state;
			state = !state;
			if(state == true){
				this.setBackground(color_true);
			}else{
				if(state_prev == true){
					hasCorpse = true;
				}
				if(hasCorpse == true){
					this.setBackground(color_corpse);
				}else{
					this.setBackground(color_false);
				}
			}
			repaint();
		}
		public boolean getState(){
			return state;
		}
		public void removeCorpse(){
			hasCorpse = false;
		}
	}//end class Cell
	
	public void setColorDead(Color color){
		color_false = color;
		refreshCorpseColor();
	}
	public void setColorAlive(Color color){
		color_true = color;
		refreshCorpseColor();
	}
	public void refreshCorpseColor(){
		if(trails == true){
			if(color_false.equals(Color.white)){
				color_corpse = Color.lightGray;
			}else if(color_false.equals(Color.black)){
				color_corpse = Color.darkGray;
			}else{
				color_corpse = color_false.darker();
			}
		}else{
			color_corpse = color_false;
		}
	}
	
	//CELL EVENT HANDLERS
	//
	/*	
	  	public void mouseEntered(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
	*/
	//CELL EVENT HANDLERS:
	private class CellListener_flip implements MouseListener{
		public void mouseEntered(MouseEvent e){
			Cell source = (Cell)(e.getSource());
			switch(mouse_action){
				case 'f':
					if(mouseState == true){
						source.flipState();
					}
					break;
				case 'k':
					if(mouseState == true)
					{
						source.setState(false);
					}
					break;
				case 'g':
					if(mouseState == true)
					{
						source.setState(true);
					}
					break;
			}
					
		}
		public void mousePressed(MouseEvent e){
			Cell source = (Cell)(e.getSource());
			switch(mouse_action){
				case 'f':
					mouseState = true;
					source.flipState();
					break;
				case 'k':
					mouseState = true;
					source.setState(false);
					break;
				case 'g':
					mouseState = true;
					source.setState(true);
					break;
			}

		}
		public void mouseReleased(MouseEvent e){
			switch(mouse_action){
				case 'f':
					mouseState = false;
					break;
				case 'k':
					mouseState = false;
					break;
				case 'g':
					mouseState = false;
					break;
			}
		}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
	}
	
	public void getControlPanel(GUI_ControlPanel cp)
	{
		this.cp = cp;
	}
	public int getSize_x(){
		return size_x;
	}
	public int getSize_y(){
		return size_y;
	}
	
	public void load_bool_array(boolean[][] arr){
		for(int i=0; i<size_x; i++){
			for(int j=0; j<size_y; j++){
				sim_grid[i][j].setState(arr[i][j]);
			}
		}
	}
	
	public boolean[][] export_bool_array(){
		boolean[][] ret_grid = new boolean[size_x][size_y];
		for(int i=0; i<size_x; i++){
			for(int j=0; j<size_y; j++){
				ret_grid[i][j] = sim_grid[i][j].getState();
			}
		}
		return ret_grid;
	}
	
	public void clear_board(){
		for(int i=0; i<size_x; i++){
			for(int j=0; j<size_y; j++){
				sim_grid[i][j].setState(false);
				sim_grid[i][j].removeCorpse();
				sim_grid[i][j].repaint();
			}
		}
		brain.clear_board();
	}
	public void nextGen(){
		brain.nextGen();
	}
	public void skip_to_gen(int gen){
		brain.skip_to_gen(gen);
	}
	public void start(){
		if(is_on == false){
			is_on = true;
			threadController = new thread_sim_run();
			timer = new java.util.Timer();
			timer.schedule(threadController, 0, simSpeed_ms);
		}
	}
	public void stop(){
		is_on = false;
		timer.cancel();
	}
	
	public boolean is_running(){
		return is_on;
	}
	
	public void setRandom(){
		for(int i=0; i<size_x; i++){
			for(int j=0; j<size_y; j++){
				System.out.println("random : " + i + ", " + j);
				boolean chk = Math.random() < 0.5;
				sim_grid[i][j].setState(chk);
			}
		}
	}
	
	public void set_mouse_action(char action){
		mouse_action = action;
	}
	
	class thread_sim_run extends TimerTask{
		public void run(){
			nextGen();
		}
	}
	
	public void setColor(Color color, boolean opt){
		if(opt == true){
			color_true = color;
		}else{
			color_false = color;
		}
		refreshCorpseColor();
	}
	
	public void setTrailsOn(){
		trails = true;
		refreshCorpseColor();
		System.out.println("Trails on\n" + color_corpse);
	}
	public void setTrailsOff(){
		trails = false;
		refreshCorpseColor();
		System.out.println("Trails off\n" + color_corpse);
	}
}

/*
TODO:
	Add edge detection to cell_check_adjacent method (before directional check calls)


*/

public class Brain{
	int grid_size_x;
	int grid_size_y;
	GUI_SimGrid simGrid;
	boolean[][] grid_current;
	boolean[][] grid_tmp;


	public Brain(GUI_SimGrid simGrid){
	this.simGrid = simGrid;
	grid_size_x = simGrid.getSize_x();
	grid_size_y = simGrid.getSize_y();
	
	grid_current 	= new boolean[grid_size_x][grid_size_y];
	grid_tmp	= new boolean[grid_size_x][grid_size_y];
	}
	
	public int cell_check_adjacent(int i, int j)
	{
		int total_adjacent_cells = 0;
	
		if(cell_check_north(i, j, grid_current))	{total_adjacent_cells++;}
		if(cell_check_south(i, j, grid_current))	{total_adjacent_cells++;}
		if(cell_check_east(i, j, grid_current))		{total_adjacent_cells++;}
		if(cell_check_west(i, j, grid_current))		{total_adjacent_cells++;}
		
		if(cell_check_northwest(i, j, grid_current))	{total_adjacent_cells++;}	
		if(cell_check_northeast(i, j, grid_current))	{total_adjacent_cells++;}
		if(cell_check_southwest(i, j, grid_current))	{total_adjacent_cells++;}
		if(cell_check_southeast(i, j, grid_current))	{total_adjacent_cells++;}
	
		return total_adjacent_cells;
	}
	
	
	
	
	
	
	public boolean cell_check_north(int i, int j, boolean[][] grid)
	{
		boolean chk;
		//out of bounds
		if(j<=0){
			chk = grid[i][grid_size_y-1];
		}
		
		else{
			chk = grid[i][j-1];
		}

		return chk;
	}
	public boolean cell_check_south(int i, int j, boolean[][] grid)
	{
		boolean chk;
		//out of bounds
		if(j>=grid_size_y-1){
			chk = grid[i][0];
		}
	
		else{
			chk = grid[i][j+1];
		}

		return chk;	
	}
	public boolean cell_check_west(int i, int j, boolean[][] grid)
	{
		boolean chk;
		//out of bounds
		if(i<=0){
			chk = grid[grid_size_x-1][j];
		}
	
		else{
			chk = grid[i-1][j];
		}

		return chk;
	}
	public boolean cell_check_east(int i, int j, boolean[][] grid)
	{
		boolean chk;
		//out of bounds
		if(i>=grid_size_x-1){
			chk = grid[0][j];
		}
	
		else{
			chk = grid[i+1][j];
		}

		return chk;	
	}
	
	public boolean cell_check_northwest(int i, int j, boolean[][] grid)
	{
		boolean chk;
		int res_i = i-1;
		int res_j = j-1;
		//out of bounds
		if(j==0){
			res_j = grid_size_y-1;
		}
		if(i==0){
			res_i = grid_size_x-1;
		}
		//System.out.println("i:\t" + i + "\t\tres_i:" + '\t' + res_i);
		//System.out.println("j:\t" + j + "\t\tres_j:" + '\t' + res_j);
		chk = grid[res_i][res_j];

		
		return chk;
	}
	public boolean cell_check_northeast(int i, int j, boolean[][] grid)
	{
		boolean chk;
		int res_i = i+1;
		int res_j = j-1;
		//out of bounds
		if(j<=0){
			res_j = grid_size_y-1;
		}
		if(i>=grid_size_x-1){
			res_i = 0;
		}
	
		chk = grid[res_i][res_j];

		return chk;
	}
	public boolean cell_check_southwest(int i, int j, boolean[][] grid)
	{
		boolean chk;
		int res_i = i-1;
		int res_j = j+1;
		//out of bounds
		if(j>=grid_size_y-1){
			res_j = 0;
		}
		if(i<=0){
			res_i = grid_size_x-1;
		}
	
		chk = grid[res_i][res_j];
		
		return chk;
	}
	public boolean cell_check_southeast(int i, int j, boolean[][] grid)
	{
		boolean chk;
		int res_i = i+1;
		int res_j = j+1;
		//out of bounds
		if(i>=grid_size_x-1){
			res_i = 0;
		}
		if(j>=grid_size_y-1){
			res_j = 0;
		}
		
		chk = grid[res_i][res_j];

		return chk;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void grid_current_write_adjacent(int grid_size_x, int grid_size_y, boolean[][] grid_current, boolean[][] grid_tmp){
		int tmp_num_adjacent;
		for(int j=0; j<grid_size_y; j++){
			for(int i=0; i<grid_size_x; i++){
				tmp_num_adjacent = cell_check_adjacent(i, j);
				System.out.print(tmp_num_adjacent + " ");
			}
			System.out.print('\n');
		}		
		System.out.print("\n========================================\n");
	}
	public void grid_current_simulate_to_tmp(int grid_size_x, int grid_size_y, boolean[][] grid_current, boolean[][] grid_tmp){
	
		/*
		start	condition	result
		-	-		-
		living 	<2 		die
		living 	==2 || ==3 	live
		living 	>3 		die
		dead   	==3 		live
		*/
		int tmp_num_adjacent;
		for(int i=0; i<grid_size_x; i++){
				for(int j=0; j<grid_size_y; j++){
					tmp_num_adjacent = cell_check_adjacent(i, j);
					
					
					
					//LIVE
					if(grid_current[i][j]==true){
						if(tmp_num_adjacent < 2){grid_tmp[i][j] = false;}
						else if(tmp_num_adjacent==2 || tmp_num_adjacent==3){grid_tmp[i][j] = true;}
						else if(tmp_num_adjacent > 3){grid_tmp[i][j] = false;}
					}
						
					//DEAD
					else{
						if(tmp_num_adjacent==3){grid_tmp[i][j] = true;
					}
				}
			}
		}
}
	
	
	public void grid_tmp_to_grid_current()
	{
		for(int i=0; i<this.grid_size_x; i++)
		{
			for(int j=0; j<this.grid_size_y; j++)
			{
				grid_current[i][j] = grid_tmp[i][j];
			}
		}
	}
	
	public void clear_board(){
		for(int i=0; i<grid_size_x; i++){
			for(int j=0; j<grid_size_y; j++){
				grid_current[i][j] = false;
				grid_tmp[i][j] = false;
			}
		}
	}
	public void current_to_simGrid(){
		simGrid.load_bool_array(grid_current);
	}
	public void simGrid_to_current(){
		grid_current = simGrid.export_bool_array();
	}
	
	public void nextGen(){
		simGrid_to_current();
		grid_current_simulate_to_tmp(grid_size_x, grid_size_y, grid_current, grid_tmp);
		grid_tmp_to_grid_current();
		current_to_simGrid();
	}
	
	public void skip_to_gen(int gen){
		for(int i=0; i<=gen; i++){
			this.nextGen();
			current_to_simGrid();
		}
	}

}//end class Brain
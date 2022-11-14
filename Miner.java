
public class Miner implements Cloneable
{
	public int X = 0, Y = 0;
	public String front = "down";
	public int nGrid;
	public int gameState = 0;
	
	
	public Miner(int n)
	{
		nGrid = n;
	}
	
	public void rotate(Contents Grid, MCO1GUI GUI)
	{
		GUI.updateRotateCounter();
		
		if(front.equalsIgnoreCase("down"))
		{
			front = "left";
			Grid.direction = "left";
		}
			
		
		else
		if(front.equalsIgnoreCase("left"))
		{
			front = "up";
			Grid.direction = "up";
		}
		
		else
		if(front.equalsIgnoreCase("up"))
		{
			front = "right";
			Grid.direction = "right";
		}
		
		else
		if(front.equalsIgnoreCase("right"))
		{
			front = "down";
			Grid.direction = "down";
		}
			
		
		Grid.repaint();
	}	
	
	public char scan(char grid[][], MCO1GUI GUI)
	{
		GUI.updateScanCounter();
		int frontX = X, frontY = Y;
		
		switch (front)
		{
			case "down":
				do
				{
					if(grid[frontX][frontY] ==  '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
						if(frontY + 1 < nGrid)
						{
						frontY++;
						}
				}while(frontY < nGrid-1 && (grid[frontX][frontY] ==  '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143 ));
				if(grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
					return '\0';
					
					else
					return grid[frontX][frontY];
				
			case "up":
				do
				{
					if(grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
						if(frontY - 1 >= 0)
						frontY--;
					
					
					
				}while(frontY > 0 && (grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143 ));
				if(grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
					return '\0';
					
					else
					return grid[frontX][frontY];
				
			case "left":
				do
				{
					if(grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
						if(frontX - 1 >= 0)
						frontX--;
					
				
					
					
				}while(frontX > 0 && (grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143 ) );

				if(grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
					return '\0';
					
				else
				return grid[frontX][frontY];
				
				
			case "right":
				do
				{
					if(grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143 )
						if(frontX + 1 < nGrid)
						frontX++;
					
					
					
				}while(frontX < nGrid-1 && (grid[frontX][frontY] == '\0' || grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143 ));
				
				if(grid[frontX][frontY] == 'M' || grid[frontX][frontY] == (char)143)
				return '\0';
				
				else
				return grid[frontX][frontY];
		}
		
		return '\0';
	}
	
	public void move(char Grid[][], Contents GameGrid, MCO1GUI GUI)
	{
		
		if(gameState == 0)
		{
		
		
		switch (front)
		{
			case "down":
				if(Y+1 >= nGrid)
					System.out.print("INVALID MOVE");
				
				else
				{
				GUI.updateMoveCounter();
				Grid[X][Y] = (char) (Grid[X][Y] - 'M');
				Y = Y + 1;
				Grid[X][Y] = (char) (Grid[X][Y] + 'M');
				GameGrid.moveDown();
				}
				
				break;
				
			case "up":
				if(Y-1 <= -1)
					System.out.print("INVALID MOVE");
				
				else
				{
				GUI.updateMoveCounter();
				Grid[X][Y] = (char) (Grid[X][Y] - 'M');
				Y = Y - 1;
				Grid[X][Y] = (char) (Grid[X][Y] + 'M');
				GameGrid.moveUp();
				}
				
				break;
				
			case "left":
				if(X-1 <= -1)
					System.out.print("INVALID MOVE");
				
				else
				{
				GUI.updateMoveCounter();
					Grid[X][Y] = (char) (Grid[X][Y] - 'M');
				X = X - 1;
				Grid[X][Y] = (char) (Grid[X][Y] + 'M');
				GameGrid.moveLeft();
				}
				
				break;
				
			case "right":
				if(X+1 >= nGrid)
					System.out.print("INVALID MOVE");
				
				else
				{
				GUI.updateMoveCounter();
				Grid[X][Y] = (char) (Grid[X][Y] - 'M');
				X = X + 1;
				Grid[X][Y] = (char) (Grid[X][Y] + 'M');
				GameGrid.moveRight();
				}
				break;
		}
		
		if(Grid[X][Y] - 'M' == 'P')
		{
			System.out.println("GAME OVER");
			GameGrid.showGameOver();
			gameState = -1;
		}
		
		else
		if(Grid[X][Y] - 'M' == 'G')
		{
			System.out.println("GAME WON");
			GameGrid.showGameWin();
			gameState = 1;
		}
		}
		
		
		
	}
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

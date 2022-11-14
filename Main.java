import java.util.Scanner;

public class Main 
{
	
	public static void main(String[] args) throws CloneNotSupportedException 
    {
		
		Scanner kb = new Scanner(System.in);
		int n;
		
		do
		{
			System.out.println("Input Grid Dimensions: ");
			n = kb.nextInt();
			
			if(n < 8)
				System.out.println("Invalid grid size, grid has to be between 8 and 64");
			
		}while(n < 8);
		
		
		
		char gameGrid[][] = new char[n][n];
		gameGrid[0][0] = 'M';
		
		//Add error checking if tile is already taken, different object cannot be placed (pwede ring ma ooverwrite yung current tile)
		try
		{
			
		int Gx, Gy;
		System.out.println("Input Golden Square Coordinates");
		System.out.print("X: ");
		Gx = kb.nextInt();
		System.out.print("Y: ");
		Gy = kb.nextInt();
		gameGrid[Gx-1][Gy-1] = 'G';
		
		
		
		System.out.println("Input Number of Beacons");
		int numBeacons = kb.nextInt();
		
		int[] beaconX = new int[numBeacons];
		int[] beaconY = new int[numBeacons];
		
		int valid = 1;
		
		for(int ctr = 1; ctr <= numBeacons; ctr++)
		{
			int Bx, By;
			
			do
			{
				
			valid = 1;
			Bx = By = 0;
			System.out.println("Coordinates of Beacon#" + ctr);
			System.out.print("X: ");
			Bx = kb.nextInt();
			System.out.print("Y: ");
			By = kb.nextInt();
			
			if(Bx == Gx && By == Gy)
			{
				valid = 0;
				System.out.println("You can't place an object on the golden tile");
			}
			
			//Check if beacons are placed on the same row/column as golden tile
			else
			if(Bx == Gx || By == Gy)
			{
			gameGrid[Bx-1][By-1] = 'B';
			beaconX[ctr-1] = Bx -1;
			beaconY[ctr-1] = By -1;
			System.out.println();
			}
			
			else
			{
			System.out.println("Beacon should be placed on the same axis as the Golden Square!");
			valid = 0;
			}
			
			
			}while(valid == 0);
		}
		
		
		System.out.println("Input Number of Pits");
		int numPits = kb.nextInt();
		
		for(int ctr = 1; ctr <= numPits; ctr++)
		{
			int Px, Py;
			
			
			valid = 1;
				
			System.out.println("Coordinates of Pit#" + ctr);
			System.out.print("X: ");
			Px = kb.nextInt();
			System.out.print("Y: ");
			Py = kb.nextInt();
			
				
				
		
			
			
			gameGrid[Px-1][Py-1] = 'P';
			System.out.println();
			
		}
		
		Contents Grid = new Contents(n, gameGrid);
		MCO1GUI GUI = new MCO1GUI(Grid);
		Miner Minecrafter = new Miner(n);
		
		
		//CHECK IF PIT/GOLD is located at the starting point of miner
		if(gameGrid[0][0] == 'G')
		{
			Minecrafter.gameState = 1;
			GUI.Grid.showGameWin();
		}
				
		else
		if(gameGrid[0][0] == 'P')
		{
			Minecrafter.gameState = -1;
			GUI.Grid.showGameOver();
		}
		
		if(Minecrafter.gameState == 0)
		{
			int choice;
			
			System.out.println("Pick an AI");
			System.out.println("#1: Random AI");
			System.out.println("#2: Smart AI 1");
			System.out.println("#3: Smart AI 2 (BFS)");
			choice = kb.nextInt();
			
			
			switch (choice)
			{
			
			case 1: int delay = 0;
					System.out.println("Choose a mode");
					System.out.println("#1: Delayed");
					System.out.println("#2: Fast View");
					delay = kb.nextInt();
					
					if(delay == 1)
						RandomAI.start(Minecrafter, GUI.Grid, gameGrid, GUI);
					
					else
						RandomAI.startnoDelay(Minecrafter, GUI.Grid, gameGrid, GUI);
					
					break;
					
			case 2: delay = 0;
					System.out.println("Choose a mode");
					System.out.println("#1: Delayed");
					System.out.println("#2: Fast View");
					delay = kb.nextInt();
					
					
					if(delay == 1)
						SmartAI.start(Minecrafter, GUI.Grid, gameGrid, GUI);
					
					else
						SmartAI.startnoDelay(Minecrafter, GUI.Grid, gameGrid, GUI);
					break;
					
			case 3: BFS.start(GUI.Grid, Minecrafter, gameGrid, GUI);
			}	
			
		}
		

		

		
		/*
		String move = "";
		move = kb.nextLine();
		
		do
		{
			switch(move)
			{
			case "m": Minecrafter.move(gameGrid, Grid, GUI);
			break;
			
			case "r": Minecrafter.rotate(Grid, GUI);
			break;
			
			case "s": System.out.println(Minecrafter.scan(gameGrid, GUI));
			}
			
			move = kb.nextLine();
		}while(move != "");
		*/
		
		
		
		}
		catch(ArrayIndexOutOfBoundsException exception)
		{
			System.out.println(exception.getMessage());
		}
		
		
			
		
		
    }

	
}

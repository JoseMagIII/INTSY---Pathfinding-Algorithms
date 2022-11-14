import java.util.ArrayList;
import java.util.Random;

public class SmartAI 
{
	
	
	public static void start(Miner Steve, Contents Grid, char gameGrid[][], MCO1GUI GUI)
	{
		
		char scan;
		int rotCnt = 0;
		int gridSize = Steve.nGrid;
		ArrayList<Integer> xLocs = new ArrayList<Integer>();
		ArrayList<Integer> yLocs = new ArrayList<Integer>();
		
		xLocs.add(0);
		yLocs.add(0);
		
		char possMoves[] = new char[4];
		

		
		while(Steve.gameState == 0)
		{
			
			
		//Initial scan
		scan = Steve.scan(gameGrid, GUI);
		
		//If Gold Tile is scanned keep moving until win
		if(scan == 'G')
			do
			{
				
				//Delay
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 Steve.move(gameGrid, Grid, GUI);
			}while(Steve.gameState != 1);
		
		//If Beacon is scanned move then scan until beacon isn't scanned anymore
		else
		if(scan == 'B')
		{
			scan = Steve.scan(gameGrid, GUI);
			do
			{
				
				//Delay
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 Steve.move(gameGrid, Grid, GUI);
				scan = Steve.scan(gameGrid, GUI);
			}while(Steve.gameState != 1 && scan == 'B');
			
			//Find gold tile
			do
			{
				//Delay
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Steve.rotate(Grid, GUI);
				scan = Steve.scan(gameGrid, GUI);
			}while(scan != 'G');
			
			//Keep moving until win
			do
			{
				//Delay
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 Steve.move(gameGrid, Grid, GUI);
			}while(Steve.gameState != 1);
		}
		
		//If pit or empty rotate 
		else
		if((scan == 'P' || scan == '\0') && rotCnt < 4)
		{
			//Delay
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			possMoves[rotCnt] = scan;
			
			Steve.rotate(Grid, GUI);
			rotCnt++;
		}
		
		
		//If after 4 rotates pit/nothing is scanned choose best move
		
		else
		{
			
			int index = -1;
			boolean isMoveValid = false;
			int checkCount = 0;
			
			//Prioritize null moves first
			
			do
			{
			//Find first null move
			for(int ctr = 0; ctr < 4 && index == -1; ctr++)
				if(possMoves[ctr] == '\0')
				{
					index = ctr;
					isMoveValid = true; //initialize move to valid first
				}
			
			//Check if move is valid
			if(index == 0)
			{
				if(Steve.Y + 1 >= gridSize)
				isMoveValid = false;
				
			}
			else
			if(index == 1)
			{
				if(Steve.X - 1 <= -1)
				isMoveValid = false;
				
			}
			else
			if(index == 2)
			{
				if(Steve.Y - 1 <= -1)
				isMoveValid = false;
			}
			else
			if(index == 3 )
			{
				
				if(Steve.X + 1 >= gridSize)
				isMoveValid = false;
			}
			
			//CHECK IF MOVE IS BACKTRACK 
			if(index != -1 && isMoveValid)
			{
				
				int futureX = 0, futureY = 0;
				
				switch(index)
				{
				case 0: futureX = Steve.X;
						futureY = Steve.Y + 1;
						break;
						
				case 1: futureX = Steve.X -1;
						futureY = Steve.Y;
						break;
						
				case 2: futureX = Steve.X;
						futureY = Steve.Y - 1;
						break;
						
				case 3: futureX = Steve.X + 1;
					 	futureY = Steve.Y;
					 	break;
				}
				
				for(int ctr = 0; ctr < xLocs.size() && isMoveValid; ctr++)
				{
					if(xLocs.get(ctr) == futureX)
						if(yLocs.get(ctr) == futureY)
						{
							isMoveValid = false;
							
						}
				}
			
			
			}
			//if move is not valid set move to invalid
			if(!isMoveValid && index != -1)
			{
			possMoves[index] = 'I';
			index = -1;
			}
			
			checkCount++;
			}while(checkCount < 4 && !isMoveValid);
			
			
			//If no null move is valid, check for valid pit moves
			if(!isMoveValid || index == -1)
			{
				
				checkCount = 0;
				ArrayList<Integer> moveSet = new ArrayList<Integer>();
				
			do
			{
				for(int ctr = 0; ctr < 4 && index == -1; ctr++)
					if(possMoves[ctr] == 'P')
					{
						index = ctr;
						isMoveValid = true;
					}
				
				//Check if pit move is backtrack
				
				int futureX = 0, futureY = 0;
				
				switch(index)
				{
				case 0: futureX = Steve.X;
						futureY = Steve.Y + 1;
						break;
						
				case 1: futureX = Steve.X -1;
						futureY = Steve.Y;
						break;
						
				case 2: futureX = Steve.X;
						futureY = Steve.Y - 1;
						break;
						
				case 3: futureX = Steve.X + 1;
					 	futureY = Steve.Y;
					 	break;
				}
				
				
				for(int ctr = 0; ctr < xLocs.size() && isMoveValid; ctr++)
				{
					if(xLocs.get(ctr) == futureX)
						if(yLocs.get(ctr) == futureY)
						{
							isMoveValid = false;
						}
				}
				
				if(!isMoveValid && index != -1)
				{
				possMoves[index] = 'I';
				index = -1;
				}
				
				//If pit move is valid add to valid pit moves
				else
				if(index != -1)
				{
					moveSet.add(index);
				}
				checkCount++;
			}while(checkCount < 4);
			
			
			//After checking for the possible pit moves, choose a random pit move
			Random rand = new Random();
			int numMoves = moveSet.size();
			
			if(numMoves > 0)
			{
			int rightMoveIndex = rand.nextInt(numMoves);
			
			switch(moveSet.get(rightMoveIndex))
			{
			case 0: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("down"));
					 Steve.move(gameGrid, Grid, GUI);
					break;
			
			case 1: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("left"));
					 Steve.move(gameGrid, Grid, GUI);
					break;
					
			case 2: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("up"));
					 Steve.move(gameGrid, Grid, GUI);
					break;
			
			case 3: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("right"));
					 Steve.move(gameGrid, Grid, GUI);
					break;
			}
			}
			
			else
			{
				int move = rand.nextInt(4);
				
				switch(move)
				{
				case 0: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("down"));
						
						 Steve.move(gameGrid, Grid, GUI);
						
						break;
				
				case 1: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("left"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
						
				case 2: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("up"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
				
				case 3: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("right"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
				}
				
			}
			
			}
			
			//If there is a valid null move, move to that valid null move
			else
			if(isMoveValid)
			{
				
				
				switch(index)
				{
				case 0: do
						{
						//Delay
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("down"));
						
						 Steve.move(gameGrid, Grid, GUI);
						
						break;
				
				case 1: do
						{
						//Delay
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("left"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
						
				case 2: do
						{
						//Delay
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("up"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
				
				case 3: do
						{
						//Delay
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("right"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
				}
			}
				
			
			xLocs.add(Steve.X);
			yLocs.add(Steve.Y);
			possMoves = new char[4];
			
			if(rotCnt == 4)
			{
				while(!(Steve.front.equalsIgnoreCase("down")))
				{
					//Delay
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Steve.rotate(Grid, GUI);
				}
				rotCnt = 0;
			}
			
		}
		
		/*
		
		//If nothing is scanned after 4 rotates, move
		else
		if(scan == '\0')
		{
			 move(Steve, Grid, gameGrid, GUI);
			rotCnt = 0;
		}
		
		//if scan is still pit just move
		else
		if(scan == 'P')
		{
			 move(Steve, Grid, gameGrid, GUI);
			rotCnt = 0;
		}
		
		*/
		

		
		
		}
		
		
	}
	

	public static void startnoDelay(Miner Steve, Contents Grid, char gameGrid[][], MCO1GUI GUI)
	{
		
		char scan;
		int rotCnt = 0;
		int gridSize = Steve.nGrid;
		ArrayList<Integer> xLocs = new ArrayList<Integer>();
		ArrayList<Integer> yLocs = new ArrayList<Integer>();
		
		xLocs.add(0);
		yLocs.add(0);
		
		char possMoves[] = new char[4];
		

		
		while(Steve.gameState == 0)
		{
			
			
		//Initial scan
		scan = Steve.scan(gameGrid, GUI);
		
		//If Gold Tile is scanned keep moving until win
		if(scan == 'G')
			do
			{
				
				
				 Steve.move(gameGrid, Grid, GUI);
			}while(Steve.gameState != 1);
		
		//If Beacon is scanned move then scan until beacon isn't scanned anymore
		else
		if(scan == 'B')
		{
			scan = Steve.scan(gameGrid, GUI);
			do
			{
				
				
				 Steve.move(gameGrid, Grid, GUI);
				scan = Steve.scan(gameGrid, GUI);
			}while(Steve.gameState != 1 && scan == 'B');
			
			//Find gold tile
			do
			{
				
				
				Steve.rotate(Grid, GUI);
				scan = Steve.scan(gameGrid, GUI);
			}while(scan != 'G');
			
			//Keep moving until win
			do
			{
				
				
				 Steve.move(gameGrid, Grid, GUI);
			}while(Steve.gameState != 1);
		}
		
		//If pit or empty rotate 
		else
		if((scan == 'P' || scan == '\0') && rotCnt < 4)
		{
			
			
			possMoves[rotCnt] = scan;
			
			Steve.rotate(Grid, GUI);
			rotCnt++;
		}
		
		
		//If after 4 rotates pit/nothing is scanned choose best move
		
		else
		{
			
			int index = -1;
			boolean isMoveValid = false;
			int checkCount = 0;
			
			//Prioritize null moves first
			
			do
			{
			//Find first null move
			for(int ctr = 0; ctr < 4 && index == -1; ctr++)
				if(possMoves[ctr] == '\0')
				{
					index = ctr;
					isMoveValid = true; //initialize move to valid first
				}
			
			//Check if move is valid
			if(index == 0)
			{
				if(Steve.Y + 1 >= gridSize)
				isMoveValid = false;
				
			}
			else
			if(index == 1)
			{
				if(Steve.X - 1 <= -1)
				isMoveValid = false;
				
			}
			else
			if(index == 2)
			{
				if(Steve.Y - 1 <= -1)
				isMoveValid = false;
			}
			else
			if(index == 3 )
			{
				
				if(Steve.X + 1 >= gridSize)
				isMoveValid = false;
			}
			
			//CHECK IF MOVE IS BACKTRACK 
			if(index != -1 && isMoveValid)
			{
				
				int futureX = 0, futureY = 0;
				
				switch(index)
				{
				case 0: futureX = Steve.X;
						futureY = Steve.Y + 1;
						break;
						
				case 1: futureX = Steve.X -1;
						futureY = Steve.Y;
						break;
						
				case 2: futureX = Steve.X;
						futureY = Steve.Y - 1;
						break;
						
				case 3: futureX = Steve.X + 1;
					 	futureY = Steve.Y;
					 	break;
				}
				
				for(int ctr = 0; ctr < xLocs.size() && isMoveValid; ctr++)
				{
					if(xLocs.get(ctr) == futureX)
						if(yLocs.get(ctr) == futureY)
						{
							isMoveValid = false;
							
						}
				}
			
			
			}
			//if move is not valid set move to invalid
			if(!isMoveValid && index != -1)
			{
			possMoves[index] = 'I';
			index = -1;
			}
			
			checkCount++;
			}while(checkCount < 4 && !isMoveValid);
			
			
			//If no null move is valid, check for valid pit moves
			if(!isMoveValid || index == -1)
			{
				
				checkCount = 0;
				ArrayList<Integer> moveSet = new ArrayList<Integer>();
				
			do
			{
				for(int ctr = 0; ctr < 4 && index == -1; ctr++)
					if(possMoves[ctr] == 'P')
					{
						index = ctr;
						isMoveValid = true;
					}
				
				//Check if pit move is backtrack
				
				int futureX = 0, futureY = 0;
				
				switch(index)
				{
				case 0: futureX = Steve.X;
						futureY = Steve.Y + 1;
						break;
						
				case 1: futureX = Steve.X -1;
						futureY = Steve.Y;
						break;
						
				case 2: futureX = Steve.X;
						futureY = Steve.Y - 1;
						break;
						
				case 3: futureX = Steve.X + 1;
					 	futureY = Steve.Y;
					 	break;
				}
				
				
				for(int ctr = 0; ctr < xLocs.size() && isMoveValid; ctr++)
				{
					if(xLocs.get(ctr) == futureX)
						if(yLocs.get(ctr) == futureY)
						{
							isMoveValid = false;
						}
				}
				
				if(!isMoveValid && index != -1)
				{
				possMoves[index] = 'I';
				index = -1;
				}
				
				//If pit move is valid add to valid pit moves
				else
				if(index != -1)
				{
					moveSet.add(index);
				}
				checkCount++;
			}while(checkCount < 4);
			
			
			//After checking for the possible pit moves, choose a random pit move
			Random rand = new Random();
			int numMoves = moveSet.size();
			
			if(numMoves > 0)
			{
			int rightMoveIndex = rand.nextInt(numMoves);
			
			switch(moveSet.get(rightMoveIndex))
			{
			case 0: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("down"));
					Steve.move(gameGrid, Grid, GUI);
					break;
			
			case 1: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("left"));
					Steve.move(gameGrid, Grid, GUI);
					break;
					
			case 2: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("up"));
					Steve.move(gameGrid, Grid, GUI);
					break;
			
			case 3: do
					{
						Steve.rotate(Grid, GUI);
					}while(!Steve.front.equalsIgnoreCase("right"));
					Steve.move(gameGrid, Grid, GUI);
					break;
			}
			}
			
			else
			{
				int move = rand.nextInt(4);
				
				switch(move)
				{
				case 0: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("down"));
						
						Steve.move(gameGrid, Grid, GUI);
						
						break;
				
				case 1: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("left"));
						Steve.move(gameGrid, Grid, GUI);
						break;
						
				case 2: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("up"));
						Steve.move(gameGrid, Grid, GUI);
						break;
				
				case 3: do
						{
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("right"));
						Steve.move(gameGrid, Grid, GUI);
						break;
				}
				
			}
			
			}
			
			//If there is a valid null move, move to that valid null move
			else
			if(isMoveValid)
			{
				
				
				switch(index)
				{
				case 0: do
						{
						
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("down"));
						
						Steve.move(gameGrid, Grid, GUI);
						
						break;
				
				case 1: do
						{
						
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("left"));
						 Steve.move(gameGrid, Grid, GUI);
						break;
						
				case 2: do
						{
						
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("up"));
						Steve.move(gameGrid, Grid, GUI);
						break;
				
				case 3: do
						{
						
							Steve.rotate(Grid, GUI);
						}while(!Steve.front.equalsIgnoreCase("right"));
						Steve.move(gameGrid, Grid, GUI);
						break;
				}
			}
				
			
			xLocs.add(Steve.X);
			yLocs.add(Steve.Y);
			possMoves = new char[4];
			
			if(rotCnt == 4)
			{
				while(!(Steve.front.equalsIgnoreCase("down")))
				{
					
					
					Steve.rotate(Grid, GUI);
				}
				rotCnt = 0;
			}
			
		}
		
		/*
		
		//If nothing is scanned after 4 rotates, move
		else
		if(scan == '\0')
		{
			 move(Steve, Grid, gameGrid, GUI);
			rotCnt = 0;
		}
		
		//if scan is still pit just move
		else
		if(scan == 'P')
		{
			 move(Steve, Grid, gameGrid, GUI);
			rotCnt = 0;
		}
		
		*/
		

		
		
		}
		
		
	}
	
	
}

import java.util.Random;

public class RandomAI 
{
	//Scanning is unnecessary because it won't help the randomAI win the game
	
	public static void start(Miner Steve, Contents Grid, char gameGrid[][], MCO1GUI GUI)
	{
		int move = 0;
		Random rand = new Random();
		
		
		
		
		do
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			move = rand.nextInt(2);
			
			switch(move)
			{
			case 0: Steve.rotate(Grid, GUI);
			break;
			
			case 1: Steve.move(gameGrid, Grid, GUI);
			break;
			}
			
			
		}while(!(Steve.gameState == -1 || Steve.gameState == 1));
	
	}
	
	public static void startnoDelay(Miner Steve, Contents Grid, char gameGrid[][], MCO1GUI GUI)
	{
		int move = 0;
		Random rand = new Random();
		
		do
		{
			
			move = rand.nextInt(2);
			
			switch(move)
			{
			case 0: Steve.rotate(Grid, GUI);
			break;
			
			case 1: Steve.move(gameGrid, Grid, GUI);
			break;
			}
			
			
		}while(!(Steve.gameState == -1 || Steve.gameState == 1));
		
		
	}
}

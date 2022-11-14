import java.util.*;

public class BFS
{

	public static void start(Contents contents, Miner miner, char[][] grid, MCO1GUI gui)
	{	
	
		int count = 0;
		List<String> list = new ArrayList<String>();
		boolean found = false;
		String temp, dir; 
		
		list.add("down ");
		list.add("right");
			
		int ind = 0;
		do
		{
				temp = list.get(ind);	
				System.out.println(temp);
				System.out.printf("Index %d, X = %d, Y = %d\n", ind, miner.X, miner.Y);
				Move(temp, contents, miner, grid, gui);
				
				if(!isRepeat(list, temp + "down ", ind))
				{
					delay();
					smartRotate("down", miner, contents, gui);
					
					dir = check("down", contents, miner, grid, gui);
					
					if(dir == "paper")
					{
						System.out.print("FOUND. SUCCESS");
						break;
					}
					else if(dir == "yes")
						list.add(temp + "down ");
				}
				
				if(!isRepeat(list, temp + "up   ", ind))
				{
					delay();
					smartRotate("up", miner, contents, gui);
					
					dir = check("up", contents, miner, grid, gui);
					
					if(dir == "paper")
					{
						System.out.print("FOUND. SUCCESS");
						break;
					}
					else if(dir == "yes")
						list.add(temp + "up   ");
				}
				
				if(!isRepeat(list, temp + "left ", ind))
				{
					delay();
					smartRotate("left", miner, contents, gui);
					
					dir = check("left", contents, miner, grid, gui);					
					if(dir == "paper")
					{
						System.out.print("FOUND. SUCCESS");
						break;
					}
					else if(dir == "yes")
						list.add(temp + "left ");
				}
				
				if(!isRepeat(list, temp + "right", ind))
				{
					delay();
					smartRotate("right", miner, contents, gui);
					
					dir = check("right", contents, miner, grid, gui);
					
					if(dir == "paper")
					{
						System.out.print("FOUND. SUCCESS");
						break;
					}
					else if(dir == "yes")
						list.add(temp + "right");
				}
				
				MoveBack(temp, contents, miner, grid, gui);
				ind++;
			
		}while(true);
		
	}
	
	public static void delay()
	{
		/*
		try {
			Thread.sleep(0);
			} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static boolean isRepeat(List<String> list, String d, int j)
	{
		boolean toReturn = false;
		
		for(int i = 0; i < j; i++)
		{
			if(list.get(i) == d)
			{
				toReturn = true;
				break;
			}
		}
		
		return toReturn;
	}
	
	public static void Move(String directions, Contents contents, Miner miner, char[][] grid, MCO1GUI gui)
	{
		System.out.println("Move");
		for(int i = 0; i < directions.length(); i += 5)
		{
			String temp = directions.substring(i, i + 5);
			String put = null;
			
			switch(temp)
			{
				case "up   ": put = "up"; break;
				case "down ": put = "down"; break;
				case "left ": put = "left"; break;
				case "right": put = "right"; break;
			}
			
			System.out.println(put);
			delay();
			smartRotate(put, miner, contents, gui);
			
			delay();
			miner.move(grid, contents, gui);
		}
		System.out.println("\n");
	}
	
	public static void MoveBack(String directions, Contents contents, Miner miner, char[][] grid, MCO1GUI gui)
	{		
		System.out.println("MoveBack");
		
		for(int i = directions.length() - 5; i >= 0; i -= 5)
		{
			String temp = directions.substring(i, i + 5);
			String put = null;
			
			switch(temp)
			{
				case "up   ": put = "down";  break;
				case "down ": put = "up";   break;
				case "left ": put = "right";  break;
				case "right": put = "left";    break;
			}
			
			System.out.println(put);
			
			delay();
			smartRotate(put, miner, contents, gui);
			
			delay();
			miner.move(grid, contents, gui);
		}
		
		System.out.println("\n");
	}
	
	public static void smartRotate(String dir, Miner miner, Contents contents, MCO1GUI gui)
	{
		while(dir != miner.front)
		{
			delay();
			miner.rotate(contents, gui);
		}
	}
	
	public static String check(String dir, Contents contents, Miner miner, char[][] grid, MCO1GUI gui)
	{
		
		switch(dir)
		{
			case "down": 

				if(miner.Y + 1 < miner.nGrid)
				{
					delay();
					
					if(miner.scan(grid, gui) != 'P')
					{
						miner.move(grid, contents, gui);
						
						if(miner.gameState == 1)
							return "paper";
						
						else if(miner.gameState == 0)
						{
							delay();
							smartRotate("up", miner, contents, gui);
							delay();
							miner.move(grid, contents, gui);
							
							return "yes";
						}
							
					}
						
				}
				
			break;
			
			case "up":
				
				if(miner.Y - 1 >= 0)
				{
					
					delay();
					
					if(miner.scan(grid, gui) != 'P')
					{
						miner.move(grid, contents, gui);
						
						if(miner.gameState == 1)
							return "paper";
						
						else if(miner.gameState == 0)
						{
							delay();
							smartRotate("down", miner, contents, gui);
							delay();
							miner.move(grid, contents, gui);
							
							return "yes";
						}
					}
						
				}
				
			break;
				
			case "left": 
				
				if(miner.X - 1 >= 0)
				{
					
					delay();
					
					if(miner.scan(grid, gui) != 'P')
					{
						miner.move(grid, contents, gui);
						
						if(miner.gameState == 1)
							return "paper";
						
						else if(miner.gameState == 0)
						{
							delay();
							smartRotate("right", miner, contents, gui);
							delay();
							miner.move(grid, contents, gui);
							
							return "yes";
						}
					}
				}
				
			break;
				
			case "right": 
				
				if(miner.X + 1 < miner.nGrid)
				{
					delay();
					
					if(miner.scan(grid, gui) != 'P')
					{
						miner.move(grid, contents, gui);
						
						if(miner.gameState == 1)
							return "paper";
						
						else if(miner.gameState == 0)
						{
							delay();
							smartRotate("left", miner, contents, gui);
							delay();
							miner.move(grid, contents, gui);
							
							return "yes";
						}
							
					}
				}
				
			break;
		}
		return null;
		
	}
}
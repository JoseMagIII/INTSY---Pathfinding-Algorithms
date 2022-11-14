import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Contents extends JPanel implements Cloneable{
	
	private Image miner;
	private Image pit;
	private Image gold;
	private Image beacon;
	private char gameGrid[][];
	private int minerX = 30, minerY = 30;
	private int n = 0;
	
	public String direction = "down";
	
	public Contents(int n, char Grid[][])
	{
		super.setDoubleBuffered(true);
		this.setBounds(10, 11, 974, 711);
		this.setPreferredSize(new Dimension(2000, 2000));
		this.n = n;
		this.gameGrid = Grid;
		

	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		
		//Painting of Miner
		if(direction.equalsIgnoreCase("down"))
		{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("MinerDown.png"));
		miner = ii.getImage();
		g2d.drawImage(miner, minerX, minerY, this);
		}
		
		else
		if(direction.equalsIgnoreCase("up"))
		{
			ImageIcon ii = new ImageIcon(this.getClass().getResource("MinerUp.png"));
			miner = ii.getImage();
			g2d.drawImage(miner, minerX, minerY, this);
		}
		
		else
		if(direction.equalsIgnoreCase("left"))
		{
			ImageIcon ii = new ImageIcon(this.getClass().getResource("MinerLeft.png"));
			miner = ii.getImage();
			g2d.drawImage(miner, minerX, minerY, this);
		}
		
		else
		if(direction.equalsIgnoreCase("right"))
		{
			ImageIcon ii = new ImageIcon(this.getClass().getResource("MinerRight.png"));
			miner = ii.getImage();
			g2d.drawImage(miner, minerX, minerY, this);
		}
		
		//Painting of Grid
		for (int x = 30; x <= 30*n; x += 30)
            for (int y = 30; y <= 30*n; y += 30)
                g2d.drawRect(x, y, 30, 30);
		
		//Painting of Other Objects
		
		
		ImageIcon ii = new ImageIcon(this.getClass().getResource("Beacon.png"));
		beacon = ii.getImage();
		
		ii = new ImageIcon(this.getClass().getResource("Pit.png"));
		pit = ii.getImage();
		
		ii = new ImageIcon(this.getClass().getResource("Gold.png"));
		gold = ii.getImage();
		
		for(int x = 1; x <= n; x++)
			for(int y = 1; y <= n; y++)
			{
				
				if(gameGrid[x-1][y-1] == 'P')
					g2d.drawImage(pit, x*30, y*30, this);
				
				else
				if(gameGrid[x-1][y-1] == 'B')
					g2d.drawImage(beacon, x*30, y*30, this);
				
				else
				if(gameGrid[x-1][y-1] == 'G')
					g2d.drawImage(gold, x*30, y*30, this);	
				
			}
	}
	
	public void moveUp()
	{	
		if((minerY-30) < 0)
		{
			showImpossible();
		}
		else
		{
		minerY = minerY - 30;
		direction = "up";
		repaint();
		}
	}
	
	public void moveDown()
	{
		if((minerY + 30) > 30*n)
		{
			showImpossible();
		}
		else
		{
		minerY = minerY + 30;
		direction = "down";
		repaint();
		}
	}
	
	public void moveLeft()
	{
		if((minerX-30) < 0)
		{
			showImpossible();
		}
		
		else
		{
		minerX = minerX - 30;
		direction = "left";
		repaint();
		}
	}
	
	public void moveRight()
	{
		if((minerX + 30) > 30*n)
		{
			showImpossible();
		}
		else
		{
		minerX = minerX + 30;
		direction = "right";
		repaint();
		}
	}
	
	public void showImpossible()
	{
		JOptionPane.showMessageDialog(null, "Impossible Move");
	}
	
	public void showGameOver()
	{
		JOptionPane.showMessageDialog(null, "Game Over");
	}
	
	public void showGameWin()
	{
		JOptionPane.showMessageDialog(null, "Search Sucessful");
	}
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
}

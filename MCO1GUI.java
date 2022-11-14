

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridLayout;



import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;


public class MCO1GUI extends JFrame{

	public int moveCnt = 0;
	private int scanCnt = 0;
	private int rotateCnt = 0;
	
	JLabel MoveCounter = new JLabel("Move Counter: " + moveCnt);
	JLabel RotateCounter = new JLabel("Rotate Counter: " + moveCnt);
	JLabel ScanCounter = new JLabel("Scan Counter: " + rotateCnt);
	
	public Contents Grid;
	
	public MCO1GUI(Contents Grid)
	{
		this.Grid = Grid;
		JScrollPane scroll = new JScrollPane(this.Grid);
		getContentPane().setLayout(new GridLayout(0,1));
		super.add(scroll);
		
		//Panel to para sa possible controls ng game kung sipagin
		JPanel newPanel = new JPanel();
		super.add(newPanel);
		newPanel.setLayout(null);
		
		
		MoveCounter.setFont(new Font("Tahoma", Font.BOLD, 31));
		MoveCounter.setBounds(10, 11, 482, 60);
		newPanel.add(MoveCounter);
		
		
		RotateCounter.setFont(new Font("Tahoma", Font.BOLD, 31));
		RotateCounter.setBounds(10, 106, 482, 60);
		newPanel.add(RotateCounter);
		
		
		ScanCounter.setFont(new Font("Tahoma", Font.BOLD, 31));
		ScanCounter.setBounds(10, 208, 482, 60);
		newPanel.add(ScanCounter);

		
		super.setTitle("MCO1");
		super.setSize(1000,1000);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		super.setLocationRelativeTo(null);
	}
	
	public void updateMoveCounter()
	{
		moveCnt++;
		MoveCounter.setText("Move Counter: " + moveCnt);
	}
	
	public void updateRotateCounter()
	{
		rotateCnt++;
		RotateCounter.setText("Rotate Counter: " + rotateCnt);
	}
	
	public void updateScanCounter()
	{
		scanCnt++;
		ScanCounter.setText("Scan Counter: " + scanCnt);
	}
	
}

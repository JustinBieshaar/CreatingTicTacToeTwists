import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	public static int ROWS = 3;
	public static int MATCH = 3;
	public static int SIZE = ROWS * ROWS;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tic-Tac-Toe! :)");
		GamePanel game = new GamePanel(new Color(0x464646));
		
		frame.add(game);
		frame.addMouseListener(game);
		frame.addMouseMotionListener(game);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{
	// thread used to run our game on
	private Thread thread;
	
	// Will be our graphics renderer to render our scene
	protected Graphics2D graphicsRender; 
	
	// used as reference to generate graphics renderer, we will basically render 
	// everything to this image
	private Image img;
	
	// background color as base background so it's not only white
	private Color backgroundColor;
	
	public Panel(Color color) {
		this.backgroundColor = color;
		
		setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		setFocusable(false);
		requestFocus();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		init(); // we will initialize the loop before it runs
		
		// get current time
		long lastTime = System.nanoTime();
		
		// 1 second to nanosecond divided by total frames per second
		double nanoSecondPerUpdate = 1000000000D / 30;
		
		// delta time to pass nanosecond between ticks/frames
		float deltaTime = 0;
		
		while(true){
			// get current nanosecond
			long now = System.nanoTime();
			// calculate delta time based on our desired frames per second
			deltaTime += (now - lastTime) / nanoSecondPerUpdate;
			// set last time to current time for next calculation
			lastTime = now;
			
			if(deltaTime >= 1){
				// tick + delta time
				update(deltaTime);
				
				// render 
				render();

				// reset delta
				deltaTime--;
			}
			
			try {
				// sleep thread to run code
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// if anything goes wrong, we see what went wrong by this exception trace
				e.printStackTrace();
			}
		}
	}
	
	public void init() {
		// generate image to collect graphics from
		img = createImage(Main.WIDTH, Main.HEIGHT);
		graphicsRender = (Graphics2D) img.getGraphics();
	}
	
	public void update(float deltaTime) {
		// this will be used by our child class
	}

	public void render() {
		// set rendering configuration
		graphicsRender.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
		graphicsRender.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
		
		// set color for background
		graphicsRender.setColor(backgroundColor);
		// draw background
		graphicsRender.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		// reset color to white so not everything is colored as our
		// background color
		graphicsRender.setColor(Color.white);
	}
	
	public void clear(){
		Graphics graphics = getGraphics();
		
		if(img != null) {
			// draw final image
			graphics.drawImage(img, 0, 0, null);
		}
		
		// dispose image to redraw
		graphics.dispose();
	}
}

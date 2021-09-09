import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Screenshaker implements IGameObject, IMarkerObserver {

	private JPanel panel;
	
	private boolean isShaking = false;
	
	private int x;
	private int y;
	
	private int intensity = 30;
	
	private float interval = 0.01f;
	private int maxShakes = 5;
	private int currentShakes = 0;
	private float currentTime = 0;
	
	public Screenshaker(JPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void update(float deltaTime) {
		if(!isShaking) {
			return;
		}
		
		// do all shake logic!
		currentTime += deltaTime - 1;
		if(currentTime > interval) {
			currentTime -= interval;
			currentShakes++;
			
			if(currentShakes > maxShakes) {
				isShaking = false;
				reset();
			} else {
				x = (int)(Math.random() * intensity) - (intensity / 2);
				y = (int)(Math.random() * intensity) - (intensity / 2);
			}
			
			panel.setLocation(x, y);
		}
	}

	@Override
	public void render(Graphics2D graphicsRender) {
	}

	@Override
	public void animated() {
		isShaking = true;
	}
	
	private void reset() {
		x = 0;
		y = 0;
		currentShakes = 0;
		currentTime = 0;
	}

}

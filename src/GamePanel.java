import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class GamePanel extends Panel implements MouseMotionListener, MouseInputListener {

	
	private Grid grid;
	
	public GamePanel(Color color) {
		super(color);
		
		grid = new Grid();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		grid.update(deltaTime);
	}
	
	@Override
	public void render() {
		super.render();
		
		grid.render(graphicsRender);
		
		super.clear();
	}
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(grid.isGameEnd()) {
			grid.reset();
		}
		
		grid.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		grid.mouseMoved(e);
	}

}

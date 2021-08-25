import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class GamePanel extends Panel implements MouseMotionListener, MouseInputListener {

	private Ultimate ultimate;
	
	public GamePanel(Color color) {
		super(color);
		
		ultimate = new Ultimate();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		ultimate.update(deltaTime);
	}
	
	@Override
	public void render() {
		super.render();
		
		ultimate.render(graphicsRender);
		
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
		ultimate.mouseReleased(e);
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
		ultimate.mouseMoved(e);
	}

}

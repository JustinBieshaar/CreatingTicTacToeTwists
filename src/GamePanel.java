import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class GamePanel extends Panel implements MouseMotionListener, MouseInputListener, IButtonObserver {

	private Grid grid;
	private AI ai;
	private Screenshaker screenshaker;
	private Menu menu;
	
	private Game activeGame;
	
	public GamePanel(Color color) {
		super(color);
		
		menu = new Menu(this);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		menu.update(deltaTime);
		//screenshaker.update(deltaTime);
		//grid.update(deltaTime);
		//ai.update(deltaTime);
		if(activeGame != null) {
			activeGame.update(deltaTime);
		}
	}
	
	@Override
	public void render() {
		super.render();
		
		//grid.render(graphicsRender);
		menu.render(graphicsRender);
		if(activeGame != null) {
			activeGame.render(graphicsRender);
		}
		
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
		//if(grid.isGameEnd()) {
		//	grid.reset();
		//}
		
		//grid.mouseReleased(e);
		
		menu.mouseReleased(e);
		if(activeGame != null) {
			activeGame.mouseReleased(e);
		}
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
		
		menu.mouseMoved(e);
		if(activeGame != null) {
			activeGame.mouseMoved(e);
		}
	}

	@Override
	public void pressed(String type) {
		System.out.println("pressed! create : " + type);
		switch (type) {
			case Menu.Classic:
				activeGame = new ClassicGame(this);
				break;
			case Menu.Ultimate:
				activeGame = new UltimateGame(this);
				break;
			case Menu.Swipe:
				activeGame = new SwipeGame(this);
				break;
			default:
				return;
		}
		
		menu.activate(false);
		
	}

	public void resetMenu() {
		menu.activate(true);
		activeGame = null;
	}

}

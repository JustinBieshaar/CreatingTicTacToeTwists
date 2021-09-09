import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

// just for identification
public abstract class Game implements IGameObject {

	@Override
	public abstract void update(float deltaTime);

	@Override
	public abstract void render(Graphics2D graphicsRender);
	
	public abstract void mouseReleased(MouseEvent e);
	
	public abstract void mouseMoved(MouseEvent e);
}

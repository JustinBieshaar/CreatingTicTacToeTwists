import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Menu implements IGameObject {

	public static final String Classic = "Classic";
	public static final String Ultimate = "Ultimate";
	public static final String Swipe = "Swipe";
	
	private Button classicButton;
	private Button ultimateButton;
	private Button swipeButton;
	
	private boolean isActive = true;
	
	public Menu(IButtonObserver buttonObserver) {
		classicButton = new Button(Main.WIDTH / 2, 100, 200, 100, Classic);
		classicButton.registerObserver(buttonObserver);
		
		ultimateButton = new Button(Main.WIDTH / 2, 250, 200, 100, Ultimate);
		ultimateButton.registerObserver(buttonObserver);
		
		swipeButton = new Button(Main.WIDTH / 2, 400, 200, 100, Swipe);
		swipeButton.registerObserver(buttonObserver);
	}
	
	@Override
	public void update(float deltaTime) {
		if(isActive) {
			updateButton(classicButton, deltaTime);
			updateButton(ultimateButton, deltaTime);
			updateButton(swipeButton, deltaTime);
		}
	}
	
	private void updateButton(Button button, float deltaTime) {
		if(button != null) {
			button.update(deltaTime);
		}
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		if(isActive) {
			renderButton(classicButton, graphicsRender);
			renderButton(ultimateButton, graphicsRender);
			renderButton(swipeButton, graphicsRender);
		}
	}
	
	private void renderButton(Button button, Graphics2D graphicsRender) {
		if(button != null) {
			button.render(graphicsRender);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(isActive) {
			checkButtonCollision(classicButton, e.getX(), e.getY() - 30);
			checkButtonCollision(ultimateButton, e.getX(), e.getY() - 30);
			checkButtonCollision(swipeButton, e.getX(), e.getY() - 30);
		}
	}
	
	private void checkButtonCollision(Button button, int x, int y) {
		if(button != null) {
			button.checkCollision(x, y);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isActive) {
			buttonMouseReleased(classicButton);
			buttonMouseReleased(ultimateButton);
			buttonMouseReleased(swipeButton);
		}
	}
	
	private void buttonMouseReleased(Button button) {
		if(button != null) {
			button.pressed();
		}
	}
	
	public void activate(boolean active) {
		this.isActive = active;
	}
}

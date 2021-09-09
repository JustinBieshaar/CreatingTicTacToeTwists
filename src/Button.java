import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Button implements IGameObject {

	public ArrayList<IButtonObserver> observers = new ArrayList<IButtonObserver>();
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private String text;
	
	private Color mainColor = new Color(0x739E8E);
	private Color highlightColor = new Color(0x8FC4B1);
	
	private boolean highlight = false;
	
	public Button(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		graphicsRender.setColor(highlight ? highlightColor : mainColor);
		
		graphicsRender.fillRect(x - (width / 2), y - (height / 2), width, height);

		graphicsRender.setColor(Color.white);
		int fontWidth = graphicsRender.getFontMetrics().stringWidth(text);
		int fontHeight = graphicsRender.getFontMetrics().getHeight();
		graphicsRender.drawString(text, x - (fontWidth / 2), y + (fontHeight / 4));
		
		graphicsRender.setColor(Color.white);
	}

	public void checkCollision(int x, int y) {
		highlight = x > this.x - (width / 2) && x < this.x + (width / 2) && y > this.y - (height / 2) && y < this.y + (height / 2);
	}
	
	public void registerObserver(IButtonObserver observer) {
		this.observers.add(observer);
	}
	
	public void pressed() {
		if(highlight) {
			observers.forEach(o -> o.pressed(text));
		}
	}
}

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Marker implements IGameObject {
	
	private BufferedImage marker;
	private IMarkerObserver observer;

	private float x;
	private float y;
	private int startX;
	private int startY;
	
	private int size;
	private float scale = 2;
	
	private boolean animateIn;
	
	private int type;
	
	private float targetX;
	private float targetY;
	
	private boolean won = false;
	private float alpha = 0;
	private float fadeSpeed = 0.05f;
	private float moveSpeed = 20f;
	private float scaleSpeed = 0.1f;
	
	public Marker(int x, int y, int startX, int startY, int size, int type) {
		this(x, y, type);
		this.startX = startX;
		this.startY = startY;
		
		this.size = size / Main.ROWS;
		this.x = startX + (x * this.size);
		this.y = startY + (y * this.size);

		System.out.println(this.x + " | " + this.y + " || start " + startX + " | " + startY + " || xy " + x + " | " + y + " || " + size);
		
		this.targetX = this.x;
		this.targetY = this.y;
	}
	
	public Marker(int x, int y, int type) {
		size = Main.WIDTH / Main.ROWS;
		this.x = x * size;
		this.y = y * size;

		this.targetX = this.x;
		this.targetY = this.y;
		
		this.animateIn = true;
		
		this.type = type % 2;
		String markerType = this.type == 0 ? "x" : "o";
		
		try {
			marker = ImageIO.read(new File("assets/" + markerType + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Marker(int type) {
		this.type = type % 2;
	}

	public void registerObserver(IMarkerObserver observer) {
		this.observer = observer;
	}

	@Override
	public void update(float deltaTime) {
		if(animateIn) {
			scaleIn(deltaTime);
		}
		if(x != targetX || y != targetY) {
			moveMarker(deltaTime);
		}
		if(won) {
			alpha += fadeSpeed;
			if(alpha >= 1) {
				alpha = 1;
				fadeSpeed *= -1;
				
				return;
			} else if(alpha <= 0.5f) {
				alpha = 0.5f;
				fadeSpeed *= -1;
				
				return;
			}
		}
	}

	private void scaleIn(float deltaTime) {
		scale -= scaleSpeed * deltaTime;
		alpha += scaleSpeed * deltaTime;
		
		boolean isScaling = scale > 1;
		boolean isFading = alpha < 1;
		if(!isScaling) {
			scale = 1;
		}
		if(!isFading) {
			alpha = 1;
		}
		if(!isScaling && !isFading) {
			animateIn = false;
			fireOnAnimated();
		}
	}
	
	private void fireOnAnimated() {
		if(observer != null) {
			observer.animated();
		}
	}


	private void moveMarker(float deltaTime) {
		int xD = 0;
		int yD = 0;
		if(x != targetX) {
			xD = x > targetX ? -1 : 1;
		}
		if(y != targetY) {
			yD = y > targetY ? -1 : 1;
		}

		x += (moveSpeed * xD) * deltaTime;
		y += (moveSpeed * yD) * deltaTime;
		
		if((xD > 0 && x > targetX) || (xD < 0 && x < targetX)) {
			x = targetX;
		}

		if((yD > 0 && y > targetY) || (yD < 0 && y < targetY)) {
			y = targetY;
		}
		
		if(x == targetX && y == targetY) {
			fireOnAnimated();
		}
	}


	@Override
	public void render(Graphics2D graphicsRender) {
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphicsRender.setComposite(ac);

		int markerSize = (int)(size * scale);
		int pivot = (size - markerSize) / 2;
		graphicsRender.drawImage(marker, (int) x + pivot, (int) y + pivot, markerSize, markerSize, null);

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		graphicsRender.setComposite(ac);
	}


	public int getType() {
		return type;
	}
	
	public void setWon(boolean won) {
		this.won = won;
	}


	public void updatePosition(int x, int y) {
		this.targetX = x * size;
		this.targetY = y * size;
	}

}

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Marker implements IGameObject {
	
	private BufferedImage marker;
	
	private float x;
	private float y;
	
	private int size;
	
	private int type;
	
	private float targetX;
	private float targetY;
	
	private boolean won = false;
	private float alpha = 1;
	private float fadeSpeed = 0.05f;
	private float moveSpeed = 10f;
	
	public Marker(int x, int y, int type) {
		size = Main.WIDTH / Main.ROWS;
		this.x = x * size;
		this.y = y * size;

		this.targetX = this.x;
		this.targetY = this.y;
		
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


	@Override
	public void update(float deltaTime) {
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
	}


	@Override
	public void render(Graphics2D graphicsRender) {
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphicsRender.setComposite(ac);
		
		graphicsRender.drawImage(marker, (int) x, (int) y, size, size, null);

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

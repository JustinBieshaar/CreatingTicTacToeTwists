import java.awt.Graphics2D;

public interface IGameObject {
	void update(float deltaTime);
	void render(Graphics2D graphicsRender);
}

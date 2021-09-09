import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Ultimate implements IGameObject {

	private ArrayList<UltimateGrid> grids = new ArrayList<UltimateGrid>(Main.SIZE);
	private UltimateGrid mainGrid;
	
	private int markerIndex = 0;
	
	private GamePanel gamePanel;
	
	public Ultimate(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		for (int i = 0; i < Main.SIZE; i++) {
			int x = i % Main.ROWS;
			int y = i / Main.ROWS;
			grids.add(new UltimateGrid(x, y, Main.WIDTH / Main.ROWS));
		}
		
		mainGrid = new UltimateGrid(0, 0, Main.WIDTH, false, false);
		
	}
	
	@Override
	public void update(float deltaTime) {
		for (UltimateGrid grid : grids) {
			grid.update(deltaTime);
		}
		
		mainGrid.update(deltaTime);
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		for (UltimateGrid grid : grids) {
			grid.render(graphicsRender);
		}
		
		mainGrid.render(graphicsRender);
		
		if(mainGrid.isGameEnd()) {
			drawEndGameOverlay(graphicsRender);
		}
	}
	


	private void drawEndGameOverlay(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0, 0, 0, (int)(225 * 0.5f)));
		
		graphicsRender.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		graphicsRender.setColor(Color.white);
		
		int winType = mainGrid.getWinner();
		if(winType == -1) {
			// tie!
			graphicsRender.drawString("It's a tie!",  195, 235);
		} else {
			// won!
			graphicsRender.drawString((winType == 0 ? "X" : "O") + " has won!",  175, 235);
		}

		graphicsRender.drawString("Press anywhere to restart! :)",  85, 260);
		
	}

	public void mouseReleased(MouseEvent e) {
		if(mainGrid.isGameEnd()) {
			gamePanel.resetMenu();
			return;
		}
		int activeX = -1;
		int activeY = -1;
		for (UltimateGrid grid : grids) {
			UltimatePlacement selectedPlacement = grid.mouseReleased(e, markerIndex);
			if(selectedPlacement != null) {
				activeX = selectedPlacement.getxIndex();
				activeY = selectedPlacement.getyIndex();
				markerIndex ++;
				
				if(grid.getWinner() >= 0) {
					mainGrid.placeMarker(grid.getX(), grid.getY(), grid.getWinner());
				}
			}
		}
		
		boolean multipleGridsActive = setActiveGrid(activeX, activeY);
	}
	
	public boolean setActiveGrid(int activeX, int activeY) {
		if(activeX >= 0 && activeY >= 0) {
			for (UltimateGrid grid : grids) {
				if(grid.getX() == activeX && grid.getY() == activeY) {
					if(grid.isGameEnd()) {
						setGridsActive();
						return true;
					}
					
					grid.setActive(true);
				} else {
					grid.setActive(false);
				}
			}
		}
		
		return false;
	}

	private void setGridsActive() {
		for (UltimateGrid grid : grids) {
			grid.setActive(!grid.isGameEnd());
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(mainGrid.isGameEnd()) {
			return;
		}
		for (UltimateGrid grid : grids) {
			grid.mouseMoved(e);
		}
	}

	private void reset() {
		for (UltimateGrid grid : grids) {
			grid.reset();
		}
		mainGrid.reset();
		markerIndex = 0;
	}

}
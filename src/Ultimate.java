import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Ultimate implements IGameObject {

	private ArrayList<Grid> grids = new ArrayList<Grid>(Main.SIZE);
	private Grid mainGrid;
	private AI ai;
	
	private int markerIndex = 0;
	
	public Ultimate() {
		for (int i = 0; i < Main.SIZE; i++) {
			int x = i % Main.ROWS;
			int y = i / Main.ROWS;
			grids.add(new Grid(x, y, Main.WIDTH / Main.ROWS));
		}
		
		mainGrid = new Grid(0, 0, Main.WIDTH, false, false);
		
		ai = new AI(mainGrid, this);
	}
	
	@Override
	public void update(float deltaTime) {
		for (Grid grid : grids) {
			grid.update(deltaTime);
		}
		
		ai.update(deltaTime);
		mainGrid.update(deltaTime);
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		for (Grid grid : grids) {
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
		if(ai.isMoving()) {
			return;
		}
		if(mainGrid.isGameEnd()) {
			reset();
			return;
		}
		int activeX = -1;
		int activeY = -1;
		for (Grid grid : grids) {
			Placement selectedPlacement = grid.mouseReleased(e, markerIndex);
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
		
		if(markerIndex % 2 == 1) {
			// o turn for AI
			ai.makeMove(grids, multipleGridsActive, markerIndex);
			markerIndex++;
		}
	}
	
	public boolean setActiveGrid(int activeX, int activeY) {
		if(activeX >= 0 && activeY >= 0) {
			for (Grid grid : grids) {
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
		for (Grid grid : grids) {
			grid.setActive(!grid.isGameEnd());
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(mainGrid.isGameEnd() || ai.isMoving()) {
			return;
		}
		for (Grid grid : grids) {
			grid.mouseMoved(e);
		}
	}

	private void reset() {
		for (Grid grid : grids) {
			grid.reset();
		}
		mainGrid.reset();
		markerIndex = 0;
	}

}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grid implements IGameObject {
	
	private ArrayList<Placement> placements = new ArrayList<Placement>(Main.SIZE);
	private Marker[][] markers;
	
	private Placement selectedMarker = null;
	
	private int gridThickness = 16;
	private int markerIndex = 0;
	private boolean gameEnd = false;
	private int winType = -1;
	
	public Grid() {
		
		markers = new Marker[Main.ROWS][Main.ROWS];
		
		for (int i = 0; i < Main.SIZE; i++) {
			int xIndex = i % Main.ROWS;
			int yIndex = i / Main.ROWS;
			int size = Main.WIDTH / Main.ROWS;
			
			placements.add(new Placement(xIndex * size, yIndex * size, xIndex, yIndex, size, size));
		}
		
		reset();
	}

	@Override
	public void update(float deltaTime) {
		for (Placement placement : placements) {
			placement.update(deltaTime);
		}
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers.length; y++) {
				if(markers[x][y] == null) {
					continue;
				}
				
				markers[x][y].update(deltaTime);
			}
		}
	}

	@Override
	public void render(Graphics2D graphicsRender) {

		for (Placement placement : placements) {
			placement.render(graphicsRender);
		}
		
		renderGrid(graphicsRender);
		
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers.length; y++) {
				if(markers[x][y] == null) {
					continue;
				}
				
				markers[x][y].render(graphicsRender);
			}
		}
		
		if(gameEnd) {
			drawEndGameOverlay(graphicsRender);
		}
	}

	private void renderGrid(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0x2e2e2e));
		
		int rowSize = Main.WIDTH / Main.ROWS;
		for (int x = 0; x < Main.ROWS + 1; x++) {
			graphicsRender.fillRect(x * rowSize - (gridThickness / 2), 0, gridThickness, Main.WIDTH);
			for (int y = 0; y < Main.ROWS + 1; y++) {
				graphicsRender.fillRect(0, y * rowSize - (gridThickness / 2), Main.WIDTH, gridThickness);
			}
		}
		
		graphicsRender.setColor(Color.white);
	}

	private void drawEndGameOverlay(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0, 0, 0, (int)(225 * 0.5f)));
		
		graphicsRender.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		graphicsRender.setColor(Color.white);
		
		if(winType == -1) {
			// tie!
			graphicsRender.drawString("It's a tie!",  195, 235);
		} else {
			// won!
			graphicsRender.drawString((winType == 0 ? "X" : "O") + " has won!",  175, 235);
		}

		graphicsRender.drawString("Press anywhere to restart! :)",  85, 260);
		
	}

	public void mouseMoved(MouseEvent e) {
		if(gameEnd) {
			return;
		}
		
		for (Placement placement : placements) {
			if(checkPlacement(placement)) {
				placement.checkCollision(e.getX(), e.getY() - 30);
			}
		}
	}
	
	private boolean checkPlacement(Placement placement) {
		Marker marker = markers[placement.getxIndex()][placement.getyIndex()];
		return markerIndex >= Main.MATCH * 2 &&
				(selectedMarker == null || selectedMarker == placement)
				? placement.isMarkerPlaced() && marker.getType() == markerIndex % 2 : !placement.isMarkerPlaced();
	}

	public void mouseReleased(MouseEvent e) {
		ArrayList<Placement> neighbours = null;
		if(selectedMarker != null) {
			neighbours = Checker.getNeighbours(selectedMarker, placements);
		}
		for (Placement placement : placements) {
			if(placement.isActive()) {
				int x = placement.getxIndex();
				int y = placement.getyIndex();
				if(markerIndex >= Main.MATCH * 2) {
					if(selectedMarker == null && markers[x][y].getType() == markerIndex % 2) {
						// first click
						selectedMarker = placement;
						selectedMarker.setSelected(true);
					} else if(selectedMarker != null) {
						// second click
						if(selectedMarker == placement) {
							selectedMarker.setSelected(false);
							selectedMarker = null;
							return;
						}
						
						if(neighbours.contains(placement)) {
							// found a match, move marker to this placement!
							Marker marker = markers[selectedMarker.getxIndex()][selectedMarker.getyIndex()];
							markers[selectedMarker.getxIndex()][selectedMarker.getyIndex()] = null;
							markers[x][y] = marker;
							marker.updatePosition(x, y);
							
							selectedMarker.set(false);
							selectedMarker.setActive(false);
							selectedMarker.setSelected(false);
							selectedMarker = null;
							
							placement.set(true);
							markerIndex++;
							checkWin();
							return;
						} else {
							// no match, toggle marker
							selectedMarker.setActive(false);
							selectedMarker.setSelected(false);
							selectedMarker = null;
							placement.set(false);
							placement.setActive(false);
							return;
						}
					}
					break;
				}
				placement.set(true);
				placeMarker(x, y);
			}
		}
	}

	public void placeMarker(int moveIndex) {
		placeMarker(moveIndex % Main.ROWS, moveIndex / Main.ROWS);
	}

	private void placeMarker(int x, int y) {
		markers[x][y] = new Marker(x, y, markerIndex);
		
		markerIndex ++;
		
		checkWin();
	}

	private void checkWin() {
		ArrayList<Marker> winLine = Checker.checkWin(markers);
		
		if(winLine != null) {
			winLine.forEach(marker -> marker.setWon(true));
			winType = winLine.get(0).getType();
			gameEnd = true;
			
		}
	}
	
	public void reset() {
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers.length; y++) {
				markers[x][y] = null;
			}
		}
		

		for (Placement placement : placements) {
			placement.set(false);
		}
		
		gameEnd = false;
		winType = -1;
		markerIndex = 0;
		selectedMarker = null;
	}
	
	public boolean isGameEnd() {
		return gameEnd;
	}

	public int getTurn() {
		return markerIndex % 2;
	}

	public Marker[][] getMarkers() {
		return markers;
	}

}

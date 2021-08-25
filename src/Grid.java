import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grid implements IGameObject {
	
	private ArrayList<Placement> placements = new ArrayList<Placement>(Main.SIZE);
	private ArrayList<Marker> winLine;
	private Marker[][] markers;
	
	private int gridThickness = 6;
	private int markerIndex = 0;
	private int markersPlaced = 0;
	
	private boolean gameEnd = false;
	private boolean isActive = true;
	
	private int winType = -1;
	
	private int x;
	private int y;
	
	private int startX;
	private int startY;
	
	private int size;
	
	private boolean drawGrid = true;
	private boolean drawActive = true;
	
	public Grid(int x, int y, int size, boolean drawGrid, boolean drawActive) {
		this(x, y, size);
		
		this.drawGrid = drawGrid;
		this.drawActive = drawActive;
	}
	
	public Grid(int x, int y, int size) {
		this.size = size;
		this.x = x;
		this.y = y;
		
		startX = x * size;
		startY = y * size;
		
		markers = new Marker[Main.ROWS][Main.ROWS];
		
		for (int i = 0; i < Main.SIZE; i++) {
			int xIndex = i % Main.ROWS;
			int yIndex = i / Main.ROWS;
			int cellSize = size / Main.ROWS;
			
			placements.add(new Placement(startX + xIndex * cellSize, startY + yIndex * cellSize, xIndex, yIndex, cellSize, cellSize));
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
		if(isActive && drawActive) {
			drawActiveBackground(graphicsRender);
		}
		
		for (Placement placement : placements) {
			placement.render(graphicsRender);
		}
		
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers.length; y++) {
				if(markers[x][y] == null) {
					continue;
				}
				
				markers[x][y].render(graphicsRender);
			}
		}
		
		if(drawGrid) {
			renderGrid(graphicsRender);
		}
	}

	private void drawActiveBackground(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0x305635));
		graphicsRender.fillRect(startX, startY, size, size);
		graphicsRender.setColor(Color.white);
	}

	private void renderGrid(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0x2e2e2e));
		
		int rowSize = size / Main.ROWS;
		for (int i = 0; i < Main.ROWS + 1; i++) {
			int thickness = gridThickness;
			if(i == 0 || i == Main.ROWS) {
				thickness *= 2;
			}
			graphicsRender.fillRect(startX + i * rowSize - (thickness / 2), startY, thickness, size);
			graphicsRender.fillRect(startX, startY + i * rowSize - (thickness / 2), size, thickness);
		}
		
		graphicsRender.setColor(Color.white);
		
		if(gameEnd) {
			//drawEndGameOverlay(graphicsRender);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(!isActive) {
			return;
		}
		
		for (Placement placement : placements) {
			placement.checkCollision(e.getX(), e.getY() - 30);
		}
	}

	public Placement mouseReleased(MouseEvent e, int markerIndex) {
		setMarkerIndex(markerIndex);
		return mouseReleased(e);
	}

	public Placement mouseReleased(MouseEvent e) {
		for (Placement placement : placements) {
			if(placement.isActive()) {
				placement.set(true);
				
				int x = placement.getxIndex();
				int y = placement.getyIndex();
				placeMarker(x, y);
				return placement;
			}
		}
		
		return null;
	}

	public void placeMarker(int moveIndex) {
		placeMarker(moveIndex % Main.ROWS, moveIndex / Main.ROWS);
	}

	public void placeMarker(int x, int y, int markerIndex) {
		setMarkerIndex(markerIndex);
		placeMarker(x, y);
	}

	private void placeMarker(int x, int y) {
		markers[x][y] = new Marker(x, y, startX, startY, size, markerIndex);
		
		markerIndex ++;
		markersPlaced ++;
		
		winLine = Checker.checkWin(markers);
		
		if(winLine != null) {
			winLine.forEach(marker -> marker.setWon(true));
			winType = winLine.get(0).getType();
			gameEnd = true;
			
		} else if(markersPlaced >= Main.SIZE) {
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
		markersPlaced = 0;
		isActive = true;
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
	
	public int getWinner() {
		return winLine == null ? -1 : winLine.get(0).getType();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void setMarkerIndex(int markerIndex) {
		this.markerIndex = markerIndex;
	}

}

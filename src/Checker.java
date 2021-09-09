import java.util.ArrayList;

public class Checker {

	public static ArrayList<Marker> checkWin(Marker[][] markers){
		ArrayList<Marker> match;
		
		for (int i = 0; i < Main.SIZE; i++) {
			int x = i % Main.ROWS;
			int y = i / Main.ROWS;
			
			// diagonal bottom left, top right
			match = checkMatch(x, y, 1, -1, i, markers);
			
			// diagonal top left, bottom right
			if(match == null) {
				match = checkMatch(x, y, 1, 1, i, markers);
			}

			// horizontal
			if(match == null) {
				match = checkMatch(x, y, 1, 0, i, markers);
			}

			// vertical
			if(match == null) {
				match = checkMatch(x, y, 0, 1, i, markers);
			}
			
			if(match != null) {
				return match;
			}
		}
		
		return null;
	}
	
	private static ArrayList<Marker> checkMatch(int x, int y, int dX, int dY, int index, Marker[][] markers){
		ArrayList<Marker> match = new ArrayList<Marker>(Main.MATCH);
		int type = -1;
		int checkCount = 0;
		
		while(checkCount < Main.ROWS && index < Main.SIZE && x >= 0 && x <= Main.ROWS - 1 && 
				y >= 0 && y <= Main.ROWS - 1) {
			boolean found = false;
			Marker marker = markers[x][y];
			if(marker != null) {
				if(type == -1) {
					type = marker.getType();
				}
				
				if(marker.getType() == type) {
					match.add(marker);
					found = true;
				}
			}
			
			if(!found && match.size() < Main.MATCH) {
				match.clear();
				type = -1;
			}
			
			
			x += dX;
			y += dY;
			index ++;
			checkCount ++;
		}
		return match.size() >= Main.MATCH ? match : null;
	}

	public static int getWinType(Marker[][] markers) {
		ArrayList<Marker> match = checkWin(markers);

		return match == null ? -1 : match.get(0).getType();
	}

	public static ArrayList<Placement> getNeighbours(Placement selectedMarker, ArrayList<Placement> placements) {
		ArrayList<Placement> result = new ArrayList<Placement>();
		int x = selectedMarker.getxIndex();
		int y = selectedMarker.getyIndex();
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int index = getIndexRelativeToXY(x + i, y + j);
				if(index >= 0 && index < placements.size()) {
					result.add(placements.get(index));
				}
			}
		}
		return result;
	}

	public static ArrayList<SwipePlacement> getNeighbours(SwipePlacement selectedMarker, ArrayList<SwipePlacement> placements) {
		ArrayList<SwipePlacement> result = new ArrayList<SwipePlacement>();
		int x = selectedMarker.getxIndex();
		int y = selectedMarker.getyIndex();
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int index = getIndexRelativeToXY(x + i, y + j);
				if(index >= 0 && index < placements.size()) {
					result.add(placements.get(index));
				}
			}
		}
		return result;
	}

	private static int getIndexRelativeToXY(int x, int y) {
		if(x < 0 || y < 0) {
			return -1;
		}
		return (Main.ROWS * y) + x;
	}
	
}

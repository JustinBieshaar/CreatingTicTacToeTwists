import java.util.ArrayList;

public class Minimax {

	private int bestMove = 0;
	
	private int counter = 0;
	
	public int getBestMove(Marker[][] markers, int requester) {
		bestMove = 0;
		counter = 0;
		minimax(markers, requester, true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		System.out.println("Minimax took " + counter + " tries");
		return bestMove;
	}

	private int minimax(Marker[][] markers, int requester, boolean requesterMove, int depth,
			int alpha, int beta) {
		counter++;
		int winner = Checker.getWinType(markers);
		if(winner >= 0 || getMarkersPlacedSize(markers) == Main.SIZE) {
			return getFieldScore(markers, requester, depth);
		}
		
		ArrayList<Integer> scores = new ArrayList<Integer>();
		int[] openMoves = getOpenSpotsIndexes(markers);
		int score = 0;
		
		for (int i = 0; i < openMoves.length; i++) {
			int x = openMoves[i] % Main.ROWS;
			int y = openMoves[i] / Main.ROWS;
			
			if(!requesterMove) {
				depth++;
			}
			
			int marker = requesterMove ? requester : requester + 1;
			markers[x][y] = new Marker(marker);
			score = minimax(markers, requester, !requesterMove, depth, alpha, beta);
			scores.add(score);
			markers[x][y] = null;
			
			//pruning
			if(requesterMove) {
				int maxValue = Math.max(Integer.MIN_VALUE, score);
				alpha = Math.max(alpha, maxValue);
				if(alpha > beta) {
					return maxValue;
				}
			} else {
				int minValue = Math.min(Integer.MAX_VALUE, score);
				beta = Math.min(beta, minValue);
				if(beta < alpha) {
					return minValue;
				}
			}
		}
		
		int scoreIndex = 0;
		if(requesterMove) {
			scoreIndex = getMax(scores);
		} else {
			scoreIndex = getMin(scores);
		}
		bestMove = openMoves[scoreIndex];
		
		return scores.get(scoreIndex);
	}
	
	private int getMax(ArrayList<Integer> scores) {
		int index = 0;
		int max = 0;
		for (int i = 0; i < scores.size(); i++) {
			if(scores.get(i) >= max) {
				index = i;
				max = scores.get(i);
			}
		}
		
		return index;
	}
	
	private int getMin(ArrayList<Integer> scores) {
		int index = 0;
		int min = 0;
		for (int i = 0; i < scores.size(); i++) {
			if(scores.get(i) <= min) {
				index = i;
				min = scores.get(i);
			}
		}
		
		return index;
	}

	private int getFieldScore(Marker[][] markers, int requester, int depth) {
		ArrayList<Marker> match = Checker.checkWin(markers);
		if(match == null) {
			return 0;
		}
		
		if(match.get(0).getType() == requester) {
			return Main.SIZE - depth;
		}
		
		return (Main.SIZE * -1) + depth;
	}

	private int[] getOpenSpotsIndexes(Marker[][] markers) {
		int[] openSpots = new int[Main.SIZE - getMarkersPlacedSize(markers)];
		int openSpotIndex = 0;
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers[x].length; y++) {
				if(markers[x][y] == null) {
					openSpots[openSpotIndex] = (y * Main.ROWS) + x;
					openSpotIndex++;
				}
			}
		}
		
		return openSpots;
	}

	private int getMarkersPlacedSize(Marker[][] markers) {
		int result = 0;
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers[x].length; y++) {
				if(markers[x][y] != null) {
					result ++;
				}
			}
		}
		return result;
	}
	
}

package advent24;

import java.util.ArrayList;
import java.util.List;

public class Tile {
	public static final char BUG_CHAR = '#';
	public static final char EMPTY_CHAR = '.';
	
	private boolean bug;
	private boolean willHaveBug;
	private List<Tile> neighbors;
	
	public Tile(boolean hasBug) {
		this.bug = hasBug;
		neighbors = new ArrayList<>();
	}
	
	public void addNeighbor(Tile neighbor) {
		neighbors.add(neighbor);
	}
	
	public boolean hasBug() {
		return bug;
	}
	
	public void determineNextState() {
		long neighboringBugs = neighbors.stream().filter(n -> n.hasBug()).count();
		if(bug) {
			willHaveBug = neighboringBugs == 1;
		} else {
			willHaveBug = neighboringBugs == 1 || neighboringBugs == 2;
		}
	}
	
	public void update() {
		bug = willHaveBug;
	}
}

package advent18;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private Tile start;
	private Tile end;
	private int distance;
	private List<Tile> tiles;
	
	public Path(Tile start, Tile end, int distance, List<Tile> tiles) {
		super();
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.tiles = new ArrayList<>(tiles);
	}

	public Tile getStart() {
		return start;
	}

	public Tile getEnd() {
		return end;
	}

	public int getDistance() {
		return distance;
	}

	public List<Tile> getDoorsRequired() {
		return new ArrayList<>(tiles);
	}
}

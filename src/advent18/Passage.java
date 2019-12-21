package advent18;

import java.awt.Point;

public class Passage extends Tile {
	
	public Passage(Point location) {
		super(location);
	}

	@Override
	public String toString() {
		return ".";
	}

	@Override
	public boolean isPassable() {
		return true;
	}
}

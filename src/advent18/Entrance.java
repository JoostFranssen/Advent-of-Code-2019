package advent18;

import java.awt.Point;

public class Entrance extends Tile {
	
	public Entrance(Point location) {
		super(location);
	}
	
	@Override
	public String toString() {
		return "@";
	}

	@Override
	public boolean isPassable() {
		return true;
	}
}

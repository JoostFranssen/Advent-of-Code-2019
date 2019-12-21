package advent18;

import java.awt.Point;
import java.util.Collection;

public abstract class Tile {
	private Point location;
	
	public Tile(Point location) {
		this.location = location;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public abstract boolean isPassable();
	
	public boolean isPassable(Collection<Character> keys) {
		return isPassable();
	}
	
	public boolean equalsLocation(Tile other) {
		return location.equals(other.location);
	}
}

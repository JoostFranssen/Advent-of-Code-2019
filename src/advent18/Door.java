package advent18;

import java.awt.Point;
import java.util.Collection;

public class Door extends Tile {
	private char label;
	
	public Door(Point location, char label) {
		super(location);
		this.label = label;
	}
	
	public char getLabel() {
		return label;
	}
	
	@Override
	public boolean isPassable(Collection<Character> keys) {
		return keys.contains(Character.toLowerCase(label));
	}

	@Override
	public String toString() {
		return Character.toString(label);
	}

	@Override
	public boolean isPassable() {
		return false;
	}
}

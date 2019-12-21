package advent18;

import java.awt.Point;
import java.util.Collection;

public class Key extends Tile {
	private char label;
	
	public Key(Point location, char label) {
		super(location);
		this.label = label;
	}
	
	public char getLabel() {
		return label;
	}
	
	@Override
	public boolean isPassable(Collection<Character> keys) {
		return true;
	}
	
	@Override
	public String toString() {
		return Character.toString(label);
	}

	@Override
	public boolean isPassable() {
		return true;
	}
}

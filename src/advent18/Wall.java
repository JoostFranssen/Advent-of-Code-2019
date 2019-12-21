package advent18;

import java.awt.Point;
import java.util.Collection;

import util.Constants;

public class Wall extends Tile {
	public Wall(Point location) {
		super(location);
	}

	@Override
	public boolean isPassable(Collection<Character> keys) {
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(Constants.BLOCK_CHAR);
	}

	@Override
	public boolean isPassable() {
		return false;
	}
}

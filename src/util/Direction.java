
package util;

import java.awt.Point;

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	private static final Direction[] values = Direction.values();
	
	public static Point getPointInDirection(Point p, Direction dir) {
		switch(dir) {
			case NORTH: return new Point(p.x, p.y - 1);
			case EAST: return new Point(p.x + 1, p.y);
			case SOUTH: return new Point(p.x, p.y + 1);
			case WEST: return new Point(p.x - 1, p.y);
			default: return null;
		}
	}
	
	public static Direction toRight(Direction dir) {
		return values[(dir.ordinal() + 1) % 4];
	}
	
	public static Direction toLeft(Direction dir) {
		return values[Integer.remainderUnsigned(dir.ordinal() - 1, 4)];
	}
	
	public static Direction opposite(Direction dir) {
		return values[(dir.ordinal() + 2) % 4];
	}
}

package advent11;

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	private static final Direction[] values = values();
	
	public static Direction right(Direction direction) {
		return values[Math.floorMod(direction.ordinal() + 1, 4)];
	}
	public static Direction left(Direction direction) {
		return values[Math.floorMod(direction.ordinal() - 1, 4)];
	}
	public static Direction opposite(Direction direction) {
		return values[Math.floorMod(direction.ordinal() + 2, 4)];
	}
}

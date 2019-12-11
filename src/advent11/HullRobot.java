package advent11;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intcode.Program;
import intcode.ProgramStatus;

public class HullRobot {
	private Tile position;
	private Direction direction;
	private Map<Point, Tile> tiles;
	private Set<Tile> paintedTiles;
	private Program program;
	
	public HullRobot(List<Long> sourceCode) {
		this(sourceCode, new Tile(0, 0, 0L));
	}
	public HullRobot(List<Long> sourceCode, Tile startingPosition) {
		position = startingPosition;
		direction = Direction.NORTH;
		paintedTiles = new HashSet<>();
		program = new Program(sourceCode);
		
		tiles = new HashMap<>();
		tiles.put(position, position);
	}
	
	public void run() {
		while(program.getStatus() != ProgramStatus.FINISHED) {
			performAction();
		}
	}
	
	private void performAction() {
		program.supplyInput(position.getColor());
		program.run();
		if(position.paint(program.getNextOutput())) {
			paintedTiles.add(position);
		}
		turn(program.getNextOutput());
		move();
	}
	
	private void turn(long direction) {
		if(direction == 0L) {
			turnLeft();
		} else {
			turnRight();
		}
	}
	
	private void turnLeft() {
		direction = Direction.left(direction);
	}
	
	private void turnRight() {
		direction = Direction.right(direction);
	}
	
	private void move() {
		move(1);
	}
	private void move(int distance) {
		int x = position.x - distance * ((direction.ordinal() - 2) % 2);
		int y = position.y - distance * ((direction.ordinal() - 1) % 2);
		Point p = new Point(x, y);
		if(tiles.containsKey(p)) {
			position = tiles.get(p);
		} else {
			position = new Tile(x, y, 0L);
			tiles.put(position, position);
		}
	}
	
	public int getPaintedTilesCount() {
		return paintedTiles.size();
	}
	
	public Collection<Tile> getTiles() {
		return tiles.values();
	}
}

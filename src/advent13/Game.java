package advent13;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intcode.Program;

public class Game {
	public static final int TILE_EMPTY = 0;
	public static final int TILE_WALL = 1;
	public static final int TILE_BLOCK = 2;
	public static final int TILE_HORIZONTAL_PADDLE = 3;
	public static final int TILE_BALL = 4;
	
	private Map<Point, Integer> tiles;
	private Program program;
	
	public Game(List<Long> sourceCode) {
		tiles = new HashMap<>();
		program = new Program(sourceCode);
	}
	
	public void run() {
		program.run();
		while(program.hasOutput()) {
			addPoint(program.getNextOutput(),program.getNextOutput(), program.getNextOutput());
		}
	}
	
	private void addPoint(long x, long y, long tile) {
		tiles.put(new Point((int)x, (int)y), (int)tile);
	}
	
	public int countTiles(int tile) {
		int count = 0;
		for(Point p : tiles.keySet()) {
			if(tiles.get(p) == tile) {
				count++;
			}
		}
		return count;
	}
	
	
}

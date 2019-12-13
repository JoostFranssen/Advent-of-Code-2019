package advent13;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intcode.Program;

public class Game {
	public static final int TILE_EMPTY = 0;
	public static final int TILE_WALL = 1;
	public static final int TILE_BLOCK = 2;
	public static final int TILE_HORIZONTAL_PADDLE = 3;
	public static final int TILE_BALL = 4;
	
	public static final int MOVE_LEFT = -1;
	public static final int STAY_STILL = 0;
	public static final int MOVE_RIGHT = 1;
	
	private Map<Point, Integer> tiles;
	private Program program;
	private long score;
	
	public Game(List<Long> sourceCode) {
		tiles = new HashMap<>();
		program = new Program(sourceCode);
	}
	
	public void run() {
		program.run();
		while(program.getOutputCount() >= 3) {
			int x = program.getNextOutput().intValue();
			int y = program.getNextOutput().intValue();
			long result = program.getNextOutput();
			
			if(x == -1 && y == 0) {
				score = result;
			} else {
				addPoint(x, y, (int)result);
			}
		}
	}
	
	public void playToFinish() {
		while(countTiles(TILE_BLOCK) > 0) {
			Point ball = getTilePosition(TILE_BALL);
			Point paddle = getTilePosition(TILE_HORIZONTAL_PADDLE);
			play((int)Math.signum(ball.x - paddle.x));
			run();
		}
	}
	
	public Point getTilePosition(int tile) {
		for(Point p : tiles.keySet()) {
			if(tiles.get(p) == tile) {
				return p;
			}
		}
		throw new IllegalStateException("No such tile");
	}
	
	private void addPoint(int x, int y, int tile) {
		tiles.put(new Point(x, y), tile);
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
	
	public long getScore() {
		return score;
	}
	
	public void play(int value) {
		switch(value) {
			case MOVE_LEFT:
			case STAY_STILL:
			case MOVE_RIGHT:
				program.supplyInput((long)value);
		}
	}
	
	@Override
	public String toString() {
		Set<Point> points = tiles.keySet();
		int minX = points.stream().map(p -> p.x).min(Integer::compare).get();
		int minY = points.stream().map(p -> p.y).min(Integer::compare).get();
		int maxX = points.stream().map(p -> p.x).max(Integer::compare).get();
		int maxY = points.stream().map(p -> p.y).max(Integer::compare).get();
		
		int[][] board = new int[maxY - minY + 1][maxX - minX + 1];
		
		for(Point p : points) {
			board[p.y - minY][p.x - minX] = tiles.get(p);
		}
		
		StringBuilder sb = new StringBuilder("Score: ");
		sb.append(getScore());
		sb.append("\n");
		for(int y = 0; y < board.length; y++) {
			for(int x = 0; x < board[y].length; x++) {
				switch(board[y][x]) {
					case TILE_EMPTY:
						sb.append(" ");
						break;
					case TILE_WALL:
						sb.append("█");
						break;
					case TILE_BLOCK:
						sb.append("#");
						break;
					case TILE_HORIZONTAL_PADDLE:
						sb.append("—");
						break;
					case TILE_BALL:
						sb.append("O");
						break;
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

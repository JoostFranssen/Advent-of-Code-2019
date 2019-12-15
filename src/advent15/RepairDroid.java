package advent15;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intcode.Program;

public class RepairDroid {
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int EAST = 4;
	
	private static final int FOUND_WALL = 0;
	private static final int FOUND_EMPTY = 1;
	private static final int FOUND_OXYGEN_TANK = 2;
	
	private Map<Point, Integer> map;
	private Point position;
	private int direction;
	private int steps;
	
	private Program program;
	
	public RepairDroid(List<Long> sourceCode) {
		program = new Program(sourceCode);
		position = new Point(0, 0);
		direction = NORTH;
		map = new HashMap<>();
		map.put(position, FOUND_EMPTY);
	}
	
	public void run() {
		RUNNING:
		while(true) {
			program.supplyInput((long)direction);
			program.run();
			if(program.hasOutput()) {
				int output = program.getNextOutput().intValue();
				int stepCountDirection = map.containsKey(getPointInDirection(direction)) ? -1 : 1;
				
				map.put(getPointInDirection(direction), output);
				switch(output) {
					case FOUND_WALL:
						direction = getLeftDirection(direction);
						break;
					case FOUND_EMPTY:
						move();
						steps += stepCountDirection;
						direction = getRightDirection(direction);
						break;
					case FOUND_OXYGEN_TANK:
						move();
						steps++;
						break RUNNING;
				}
			}
		}
	}
	
	public int getSteps() {
		return steps;
	}
	
	public void tryMove(int dir) {
		direction = dir;
		program.supplyInput((long)direction);
		program.run();
		if(program.hasOutput()) {
			int output = program.getNextOutput().intValue();
			map.put(getPointInDirection(direction), output);
			if(output == FOUND_EMPTY) {
				move();
			}
		}
	}
	
	private int getLeftDirection(int dir) {
		switch(dir) {
			case NORTH:
				return WEST;
			case WEST:
				return SOUTH;
			case SOUTH:
				return EAST;
			case EAST:
				return NORTH;
			default:
				throw new IllegalArgumentException("Invalid direction");
		}
	}
	
	private int getRightDirection(int dir) {
		switch(dir) {
			case NORTH:
				return EAST;
			case WEST:
				return NORTH;
			case SOUTH:
				return WEST;
			case EAST:
				return SOUTH;
			default:
				throw new IllegalArgumentException("Invalid direction");
		}
	}
	
	private Point getPointInDirection(int dir) {
		Point point = new Point(position);
		switch(dir) {
			case NORTH:
				point.y -= 1;
				break;
			case SOUTH:
				point.y += 1;
				break;
			case WEST:
				point.x -= 1;
				break;
			case EAST:
				point.x += 1;
				break;
		}
		return point;
	}
	
	private void move() {
		position = getPointInDirection(direction);
	}
	
	public void printMap() {
		Set<Point> points = map.keySet();
		int minX = points.stream().map(p -> p.x).min(Integer::compare).get();
		int minY = points.stream().map(p -> p.y).min(Integer::compare).get();
		int maxX = points.stream().map(p -> p.x).max(Integer::compare).get();
		int maxY = points.stream().map(p -> p.y).max(Integer::compare).get();
		
		Integer[][] board = new Integer[maxY - minY + 1][maxX - minX + 1];
		
		for(Point p : points) {
			board[p.y - minY][p.x - minX] = map.get(p);
		}
		
		StringBuilder sb = new StringBuilder("");
		for(int y = 0; y < board.length; y++) {
			for(int x = 0; x < board[y].length; x++) {
				if(x == -minX && y == -minY) {
					sb.append("S");
					continue;
				}
				if(position.equals(new Point(x + minX, y + minY))) {
					sb.append("D");
					continue;
				}
				if(board[y][x] == null) {
					sb.append(" ");
					continue;
				}
				switch(board[y][x]) {
					case FOUND_EMPTY:
						sb.append(".");
						break;
					case FOUND_WALL:
						sb.append("█");
						break;
					case FOUND_OXYGEN_TANK:
						sb.append("#");
						break;
				}
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

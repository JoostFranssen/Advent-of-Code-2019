package advent18;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Maze {
	private int width, height;
	private Map<Point, Character> maze;
	private List<Point> visited;
	private Set<Character> keys;
	private Point position;
	private int steps;
	private boolean retrievedKey;
	
	public Maze(List<char[]> points) {
		visited = new ArrayList<>();
		keys = new HashSet<>();
		maze = new HashMap<>();
		for(int y = 0; y < points.size(); y++) {
			char[] line = points.get(y);
			for(int x = 0; x < line.length; x++) {
				Point p = new Point(x, y);
				maze.put(p, line[x]);
				if(line[x] == '@') {
					position = p;
				}
			}
		}
		height = points.size();
		width = points.get(0).length;
	}
	
	private Point getRightPoint() {
		if(position.x == width - 1) {
			return null;
		}
		return new Point(position.x + 1, position.y);
	}
	private Point getLeftPoint() {
		if(position.x == 0) {
			return null;
		}
		return new Point(position.x - 1, position.y);
	}
	private Point getUpPoint() {
		if(position.y == 0) {
			return null;
		}
		return new Point(position.x, position.y - 1);
	}
	private Point getDownPoint() {
		if(position.y == height - 1) {
			return null;
		}
		return new Point(position.x, position.y + 1);
	}
	
	private void processMove() {
		steps += visited.contains(position) && !retrievedKey ? -1 : 1;
		visited.add(position);
		if(isKey(maze.get(position))) {
			keys.add(maze.get(position));
		}
	}
	
	private boolean isKey(char c) {
		return c >= 'a' && c <= 'z';
	}
}

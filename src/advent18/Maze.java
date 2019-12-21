package advent18;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import util.Direction;

public class Maze {
	private int width, height;
	private Tile[][] maze;
	private Map<Character, Key> keys;
	private Map<Character, Door> doors;
	private Entrance entrance;
	
	public Maze(List<char[]> points) {
		width = points.get(0).length;
		height = points.size();
		maze = new Tile[height][width];
		keys = new HashMap<>();
		doors = new HashMap<>();
		for(int y = 0; y < points.size(); y++) {
			char[] line = points.get(y);
			for(int x = 0; x < line.length; x++) {
				Point p = new Point(x, y);
				maze[y][x] = (createTileFromChar(p, line[x]));
				if(line[x] == '@') {
					entrance = (Entrance)maze[y][x];
				}
				if(Character.getType(line[x]) == Character.LOWERCASE_LETTER) {
					keys.put(line[x], (Key)maze[y][x]);
				}
				if(Character.getType(line[x]) == Character.UPPERCASE_LETTER) {
					doors.put(line[x], (Door)maze[y][x]);
				}
			}
		}
		height = points.size();
		width = points.get(0).length;
	}
	
	private static Tile createTileFromChar(Point p, char tileType) {
		switch(tileType) {
			case '#': return new Wall(p);
			case '.': return new Passage(p);
			case '@': return new Entrance(p);
			default:
				if(Character.isLowerCase(tileType)) {
					return new Key(p, tileType);
				} else {
					return new Door(p, tileType);
				}
		}
	}
	
	public Map<Character, Set<Character>> determineKeysBehindDoors() {
		Map<Character, Set<Character>> doorsBlockingKey = new HashMap<>();
		Set<Character> doorsEncountered = new HashSet<>();
		
		Point position = Direction.getPointInDirection(entrance.getLocation(), Direction.SOUTH); //do not start in the center, because it yields a circular motion
		Direction direction = Direction.NORTH;
		
		while(!doorsBlockingKey.keySet().containsAll(keys.keySet())) {
			if(maze[position.y][position.x] instanceof Door) {
				Character door = ((Door)maze[position.y][position.x]).getLabel();
				if(doorsEncountered.contains(door)) {
					doorsEncountered.remove(door);
				} else {
					doorsEncountered.add(door);
				}
			}
			
			if(maze[position.y][position.x] instanceof Key) {
				Character key = ((Key)maze[position.y][position.x]).getLabel();
				if(!doorsBlockingKey.containsKey(key)) {
					doorsBlockingKey.put(key, new HashSet<>(doorsEncountered));
				}
			}
			
			Point nextPosition;
			
			Point pointRight = Direction.getPointInDirection(position, Direction.toRight(direction));
			Point pointAhead = Direction.getPointInDirection(position, direction);
			if(maze[pointRight.y][pointRight.x].isPassable(keys.keySet())) {
				direction = Direction.toRight(direction);
				nextPosition = Direction.getPointInDirection(position, direction);
			} else if(!maze[pointAhead.y][pointAhead.x].isPassable(keys.keySet())) {
				direction = Direction.toLeft(direction);
				continue;
			} else {
				nextPosition = pointAhead;
			}
			position = nextPosition;
		}
		return doorsBlockingKey;
	}
	
	private Path shortestPath(Tile start, Tile end) {
		return shortestPath(start, end, keys.values().stream().map(k -> k.getLabel()).collect(Collectors.toList()));
	}
	private Path shortestPath(Tile start, Tile end, Collection<Character> keys) {
		Set<Point> tilesForPath = new HashSet<>();
		Set<Point> tilesVisited = new HashSet<>();
		Point position = start.getLocation();
		Direction direction = Direction.NORTH;
		int steps = 0;
		
		while(true) {
			Point nextPosition;
			
			Point pointRight = Direction.getPointInDirection(position, Direction.toRight(direction));
			Point pointAhead = Direction.getPointInDirection(position, direction);
			if(maze[pointRight.y][pointRight.x].isPassable(keys)) {
				direction = Direction.toRight(direction);
				nextPosition = Direction.getPointInDirection(position, direction);
			} else if(!maze[pointAhead.y][pointAhead.x].isPassable(keys)) {
				direction = Direction.toLeft(direction);
				continue;
			} else {
				nextPosition = pointAhead;
			}
			
			if(tilesVisited.contains(nextPosition)) {
				steps--;
				tilesForPath.remove(position);
			} else {
				steps++;
				tilesForPath.add(position);
			}
			tilesVisited.add(nextPosition);
			
			position = nextPosition;
			
			if(position.equals(end.getLocation())) {
				tilesForPath.add(position);
				break;
			}
			if(steps >= width * height) {
				return null;
			}
		}
		return new Path(start, end, steps, tilesForPath.stream().map(p -> maze[p.y][p.x]).collect(Collectors.toList()));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				sb.append(maze[y][x]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toString(Point position, Direction direction) {
		String representation = (new String[] { "^", ">", "ˇ", "<" })[direction.ordinal()];
		String string = toString();
		int stringPos = position.x + position.y * (width + 1);
		return string.substring(0, stringPos) + representation + (stringPos < string.length() ? string.substring(stringPos + 1) : "");
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile[][] getMaze() {
		return maze;
	}

	public Map<Character, Key> getKeys() {
		return new HashMap<>(keys);
	}

	public Map<Character, Door> getDoors() {
		return new HashMap<>(doors);
	}

	public Entrance getEntrance() {
		return entrance;
	}
}

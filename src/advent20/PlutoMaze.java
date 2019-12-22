package advent20;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import util.Direction;

public class PlutoMaze {
	private PlutoPassage start, end;
	
	public PlutoMaze(List<char[]> input) {
		//create passages
		Map<Point, PlutoPassage> mazeByPoints = new HashMap<>();
		for(int y = 0; y < input.size(); y++) {
			char[] line = input.get(y);
			for(int x = 0; x < line.length; x++) {
				if(line[x] == '.') {
					mazeByPoints.put(new Point(x, y), new PlutoPassage());
				}
			}
		}
		//set neighbors and teleportations
		Map<String, Point> teleportations = new HashMap<>();
		for(Point p : mazeByPoints.keySet()) {
			PlutoPassage passageP = mazeByPoints.get(p);
			List<Point> surroundingP = getSurroundingPoints(p);
			for(Point s : surroundingP) {
				if(mazeByPoints.containsKey(s)) {
					passageP.addNeighbor(mazeByPoints.get(s));
				}
				if(Character.isLetter(input.get(s.y)[s.x])) {
					String label = String.valueOf(input.get(s.y)[s.x]);
					for(Direction dir : Direction.values()) {
						Point ss = Direction.getPointInDirection(s, dir);
						if(Character.isLetter(input.get(ss.y)[ss.x])) {
							if(ss.y < s.y || ss.x < s.x) {
								label = input.get(ss.y)[ss.x] + label; 
							} else {
								label += input.get(ss.y)[ss.x];
							}
							Point source = Direction.getPointInDirection(s, Direction.opposite(dir));
							if(teleportations.containsKey(label)) {
								Point target = teleportations.get(label);
								PlutoPassage targetPassage = mazeByPoints.get(target);
								PlutoPassage sourcePassage = mazeByPoints.get(source);
								targetPassage.addNeighbor(sourcePassage);
								sourcePassage.addNeighbor(targetPassage);
							} else {
								teleportations.put(label, source);
							}
							break;
						}
					}
					
				}
			}
		}
		start = mazeByPoints.get(teleportations.get("AA"));
		end = mazeByPoints.get(teleportations.get("ZZ"));
	}
	
	public int findShortestPathLength(PlutoPassage start, PlutoPassage end) {
		Set<PlutoPassage> visited = new HashSet<>();
		Map<PlutoPassage, Integer> distanceToStart = new HashMap<>();
		Queue<PlutoPassage> queue = new ArrayDeque<>();
		queue.offer(start);
		visited.add(start);
		distanceToStart.put(start, 0);
		while(!queue.isEmpty()) {
			PlutoPassage vertex = queue.poll();
			if(vertex.equals(end)) {
				return distanceToStart.get(vertex);
			}
			for(PlutoPassage neighbor : vertex.getNeighbors()) {
				if(visited.add(neighbor)) {
					distanceToStart.put(neighbor, distanceToStart.get(vertex) + 1);
					queue.offer(neighbor);
				}
			}
		}
		throw new NoSuchElementException();
	}
	
	private List<Point> getSurroundingPoints(Point p) {
		return Arrays.asList(Direction.values()).stream().map(d -> Direction.getPointInDirection(p, d)).collect(Collectors.toList());
	}

	public PlutoPassage getStart() {
		return start;
	}

	public PlutoPassage getEnd() {
		return end;
	}
}

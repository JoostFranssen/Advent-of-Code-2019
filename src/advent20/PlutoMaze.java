package advent20;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
		int height = input.size();
		int width = input.get(0).length;
		
		for(int y = 0; y < height; y++) {
			char[] line = input.get(y);
			for(int x = 0; x < width; x++) {
				if(line[x] == '.') {
					PlutoPassage passage;
					if(Character.isLetter(line[x + 1]) || Character.isLetter(line[x - 1]) || Character.isLetter(input.get(y - 1)[x]) || Character.isLetter(input.get(y + 1)[x])) {
						passage = new PlutoPortal(new Point(x, y), (x <= 2 || width - x <= 3) || (y <= 2 || height - y <= 3));
					} else {
						passage = new PlutoPassage(new Point(x, y));
					}
					mazeByPoints.put(new Point(x, y), passage);
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
	
	public int findShortestPathLength() {
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
	
	public int findShortestPathLengthRecursive() {
		List<Set<PlutoPassage>> visitedByLevel = new ArrayList<>();
		List<Map<PlutoPassage, Integer>> distanceToStartByLevel = new ArrayList<>();
		Queue<PlutoPassage> passageQueue = new ArrayDeque<>();
		Queue<Integer> levelQueue = new ArrayDeque<>();
		passageQueue.offer(start);
		levelQueue.offer(0);
		visitedByLevel.add(new HashSet<>());
		visitedByLevel.get(0).add(start);
		distanceToStartByLevel.add(new HashMap<>());
		distanceToStartByLevel.get(0).put(start, 0);
		
		while(!passageQueue.isEmpty()) {
			PlutoPassage passage = passageQueue.poll();
			int level = levelQueue.poll();
			if(level == 0) {
				if(passage.equals(end)) {
					return distanceToStartByLevel.get(0).get(passage);
				}
			}
			for(PlutoPassage neighbor : passage.getNeighbors()) {
				int neighborLevel = level;
				
				if(neighbor instanceof PlutoPortal) {
					if(passage instanceof PlutoPortal) {
						PlutoPortal passagePortal = (PlutoPortal)passage;
						neighborLevel += passagePortal.isOuter() ? -1 : 1;
						if(visitedByLevel.size() <= neighborLevel) {
							visitedByLevel.add(new HashSet<>());
							distanceToStartByLevel.add(new HashMap<>());
						}
					}
					
					PlutoPortal neighborPortal = (PlutoPortal)neighbor;
					if(neighborLevel == 0) {
						if(neighborPortal.isOuter() && !(neighborPortal.equals(end) || neighborPortal.equals(start))) {
							continue;
						}
					} else {
						if(neighborPortal.equals(start) || neighborPortal.equals(end)) {
							continue;
						}
					}
				}
				if(visitedByLevel.get(neighborLevel).add(neighbor)) {
					distanceToStartByLevel.get(neighborLevel).put(neighbor, distanceToStartByLevel.get(level).get(passage) + 1);
					passageQueue.offer(neighbor);
					levelQueue.offer(neighborLevel);
				}
			}
		}
		throw new NoSuchElementException();
	}
	
	private List<Point> getSurroundingPoints(Point p) {
		return Arrays.asList(Direction.values()).stream().map(d -> Direction.getPointInDirection(p, d)).collect(Collectors.toList());
	}
}

package advent18;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import util.Direction;

public class Maze {
	private Passage start;
	private Map<Character, Key> keys;
	private Map<Character, Door> doors;
	
	public Maze(List<char[]> data) {
		keys = new HashMap<>();
		doors = new HashMap<>();
		
		Map<Point, Passage> passages = createPassages(data);
		setNeighbors(passages);
	}

	private Map<Point, Passage> createPassages(List<char[]> data) {
		Map<Point, Passage> passages = new HashMap<>();
		
		for(int y = 0; y < data.size(); y++) {
			char[] line = data.get(y);
			for(int x = 0; x < line.length; x++) {
				if(line[x] == '.') {
					Passage passage = new Passage();
					passages.put(new Point(x, y), passage);
				} else if(line[x] == '@') {
					start = new Passage();
					passages.put(new Point(x, y), start);
				} else if(Character.isLowerCase(line[x])) {
					Key key = new Key(line[x]);
					keys.put(line[x], key);
					passages.put(new Point(x, y), key);
				} else if(Character.isUpperCase(line[x])) {
					Door door = new Door(line[x]);
					doors.put(line[x], door);
					passages.put(new Point(x, y), door);
				}
			}
		}
		return passages;
	}

	private void setNeighbors(Map<Point, Passage> passages) {
		for(Point point : passages.keySet()) {
			Passage passage = passages.get(point);
			for(Direction dir : Direction.values()) {
				Point neighborPoint = Direction.getPointInDirection(point, dir);
				if(passages.containsKey(neighborPoint)) {
					Passage neighbor = passages.get(neighborPoint);
					passage.addNeighbor(neighbor);
				}
			}
		}
	}
	
	public Key getKeyByLabel(char label) {
		return keys.get(label);
	}
	
	public Set<Key> getKeysByLabel(Collection<Character> labels) {
		Set<Key> selectedKeys = new HashSet<>();
		for(char label : labels) {
			selectedKeys.add(keys.get(label));
		}
		return selectedKeys;
	}
	
	public Door getDoorByLabel(char label) {
		return doors.get(label);
	}
	
	public Passage getStart() {
		return start;
	}
	
	public Path findShortestPath(Passage start, Passage end) {
		//Lee's algorithm
		Set<Passage> visited = new HashSet<>();
		Map<Passage, Integer> distanceToStart = new HashMap<>();
		Queue<Passage> queue = new ArrayDeque<>();
		
		visited.add(start);
		distanceToStart.put(start, 0);
		queue.add(start);
		
		while(!queue.isEmpty()) {
			Passage passage = queue.poll();
			if(passage.equals(end)) {
				break;
			}
			for(Passage neighbor : passage.getNeighbors()) {
				if(visited.add(neighbor)) {
					distanceToStart.put(neighbor, distanceToStart.get(passage) + 1);
					queue.offer(neighbor);
				}
			}
		}
		
		//backtrack over the found path to determine the doors and keys on it
		List<Key> keysOnPath = new ArrayList<>();
		List<Door> doorsOnPath = new ArrayList<>();
		Passage passage = end;
		if(passage instanceof Key) {
			keysOnPath.add((Key)passage);
		}
		if(passage instanceof Door) {
			doorsOnPath.add((Door)passage);
		}
		
		while(!passage.equals(start)) {
			int distance = distanceToStart.get(passage);
			for(Passage neighbor : passage.getNeighbors()) {
				if(distanceToStart.containsKey(neighbor) && distanceToStart.get(neighbor) == distance - 1) {
					distance--;
					passage = neighbor;
					break;
				}
			}
			if(passage instanceof Key) {
				keysOnPath.add((Key)passage);
			}
			if(passage instanceof Door) {
				doorsOnPath.add((Door)passage);
			}
		}
		
		return new Path(start, end, distanceToStart.get(end), doorsOnPath, keysOnPath);
	}
	
	public List<Path> getPathsToReachableKeys(Passage start, Set<Key> providedKeys) {
		if(start instanceof Key) {
			providedKeys.add((Key)start);
		}
		List<Path> paths = new ArrayList<>();
		
		Set<Key> targetKeys = new HashSet<>(keys.values());
		targetKeys.removeAll(providedKeys);
		
		for(Key key : targetKeys) {
			paths.add(findShortestPath(start, key));
		}
				
		return paths;
	}
	
	public List<Path> getPathsToReachableKeysWithoutIntermediateKeys(Passage start, Set<Key> providedKeys) {
		if(start instanceof Key) {
			providedKeys.add((Key)start);
		}
		List<Path> paths = getPathsToReachableKeys(start, providedKeys);
		paths.removeIf(p -> {
			List<Key> keysOnPath = p.getKeys();
			keysOnPath.removeAll(providedKeys);
			return keysOnPath.size() > 1;
		});
		return paths;
	}
	
	public List<Path> getPathsToKeys(Passage start) {
		List<Path> paths = new ArrayList<>();
		
		Set<Key> targetKeys = new HashSet<>(keys.values());
		if(start instanceof Key) {
			targetKeys.remove((Key)start);
		}
		
		for(Key key : targetKeys) {
			paths.add(findShortestPath(start, key));
		}
		
		return paths;
	}
	
	public Node createGraph() {
		Node startNode = new Node(Node.START_LABEL);
		
		Map<Character, Node> nodesByKey = new HashMap<>();
		nodesByKey.put(Node.START_LABEL, startNode);
		
		for(char key : keys.keySet()) {
			nodesByKey.put(key, new Node(key));
		}
		
		for(char key : nodesByKey.keySet()) {
			Node node = nodesByKey.get(key);
			List<Path> pathsToKeys = getPathsToKeys(key == Node.START_LABEL ? start : keys.get(key));
			for(Path path : pathsToKeys) {
				char targetKey = ((Key)path.getEnd()).getLabel();
				Set<Character> keysNecessary = path.getDoors().stream().map(d -> Character.toLowerCase(d.getLabel())).collect(Collectors.toSet());
				keysNecessary.addAll(path.getKeys().stream().map(k -> k.getLabel()).filter(k -> k != targetKey).collect(Collectors.toSet()));
				node.addNeighbor(new Edge(nodesByKey.get(targetKey), path.getLength(), keysNecessary));
			}
		}
		
		return startNode;
	}
	
	public Optional<Integer> findShortestDistanceToKeysFrom(Node node, Set<Character> obtainedKeys) {
		if(obtainedKeys.size() == keys.size()) {
			return Optional.of(0);
		}
		
		List<Integer> distances = new ArrayList<>();
		for(Edge edge : node.getNeighbors()) {
			if(obtainedKeys.containsAll(edge.getNecessaryKeys())) {
				Node neighbor = edge.getTarget();
				if(!obtainedKeys.contains(neighbor.getKey())) {
					Set<Character> newlyObtainedKeys = new HashSet<>(obtainedKeys);
					newlyObtainedKeys.add(neighbor.getKey());
					Optional<Integer> distance = findShortestDistanceToKeysFrom(neighbor, newlyObtainedKeys);
					if(distance.isPresent()) {
						distances.add(distance.get() + edge.getLength());
					}
				}
			}
		}
		return distances.stream().min(Integer::compare);
	}
	
	private Optional<Integer> currentShortestDistance;
	
	private void findShortestDistanceToAllKeysFrom(Node start, Set<Character> obtainedKeys, int currentDistance) {
		if(currentShortestDistance.isPresent()) {
			if(currentDistance >= currentShortestDistance.get()) {
				return;
			}
		}
		
		if(obtainedKeys.size() == keys.keySet().size()) {
			if(currentShortestDistance.isEmpty() || currentShortestDistance.get() > currentDistance) {
				currentShortestDistance = Optional.of(currentDistance);
			}
		}
		
		for(Edge edge : start.getNeighbors()) {
			if(obtainedKeys.containsAll(edge.getNecessaryKeys())) {
				Node neighbor = edge.getTarget();
				Set<Character> newObtainedKeys = new HashSet<>(obtainedKeys);
				if(newObtainedKeys.add(neighbor.getKey())) {
					findShortestDistanceToAllKeysFrom(neighbor, newObtainedKeys, currentDistance + edge.getLength());
				}
			}
		}
	}
	
	public int findShortestDistanceToAllKeys() {
		Node start = createGraph();
		currentShortestDistance = Optional.empty();
		
		findShortestDistanceToAllKeysFrom(start, new HashSet<>(), 0);
		return currentShortestDistance.get();
	}
}

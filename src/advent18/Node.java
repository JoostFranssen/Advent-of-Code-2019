package advent18;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Node {
	public static final char NO_KEY = '*';
	
	private char key;
	private Map<Node, Integer> neighbors;
	
	public Node() {
		this(NO_KEY);
	}
	public Node(char key) {
		this.key = key;
		neighbors = new HashMap<>();
	}
	
	public void addNeighbor(Node node, int distance) {
		neighbors.put(node, distance);
	}
	
	public char getKey() {
		return key;
	}
	
	public Set<Node> getNeighbors() {
		return neighbors.keySet();
	}
	
	public int getDistanceToNeighbor(Node neighbor) {
		return neighbors.get(neighbor);
	}
}

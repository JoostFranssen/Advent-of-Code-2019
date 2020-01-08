package advent18;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public static final char START_LABEL = '@';
	public static final char END_LABEL = '*';
	
	private char key;
	private List<Edge> edges;
	
	public Node() {
		this(START_LABEL);
	}
	public Node(char key) {
		this.key = key;
		edges = new ArrayList<>();
	}
	
	public List<Edge> getNeighbors() {
		return new ArrayList<>(edges);
	}
	
	public void addNeighbor(Edge edge) {
		int i;
		for(i = 0; i < edges.size(); i++) {
			if(edges.get(i).getLength() > edge.getLength()) {
				break;
			}
		}
		edges.add(i, edge);
	}
	
	public char getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		return String.valueOf(key);
	}
}

package advent20;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlutoPassage {
	private Set<PlutoPassage> neighbors;
	
	public PlutoPassage() {
		neighbors = new HashSet<>();
	}
	
	public void addNeighbor(PlutoPassage neighbor) {
		neighbors.add(neighbor);
	}
	
	public void print() {
		System.out.println(String.valueOf(this) + ": " + neighbors.stream().map(n -> n.toString()).reduce((x, y) -> x + ", " + y).get());
	}
	
	public List<PlutoPassage> getNeighbors() {
		return new ArrayList<>(neighbors);
	}
}

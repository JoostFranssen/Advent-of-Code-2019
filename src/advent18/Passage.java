package advent18;

import java.util.ArrayList;
import java.util.List;

public class Passage {
	protected List<Passage> neighbors;
	
	public Passage() {
		neighbors = new ArrayList<>();
	}
	
	public void addNeighbor(Passage neighbor) {
		neighbors.add(neighbor);
	}
	
	public List<Passage> getNeighbors() {
		return new ArrayList<>(neighbors);
	}
}

package advent18;

import java.util.HashSet;
import java.util.Set;

public class Edge {
	private Set<Character> necessaryKeys;
	private int length;
	private Node target;
	
	public Edge(Node target, int length, Set<Character> necessaryKeys) {
		this.target = target;
		this.necessaryKeys = new HashSet<>(necessaryKeys);
		this.length = length;
	}
	
	public Node getTarget() {
		return target;
	}

	public Set<Character> getNecessaryKeys() {
		return new HashSet<>(necessaryKeys);
	}

	public int getLength() {
		return length;
	}
	
	@Override
	public String toString() {
		return length + " -" + necessaryKeys + "-> " + target;
	}
}

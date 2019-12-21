package advent18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyNode {
	private Set<Character> keysObtained;
	private List<KeyNode> children;
	
	public KeyNode(Set<Character> keysObtained) {
		this.keysObtained = new HashSet<>(keysObtained);
		children = new ArrayList<>();
	}
	
	public void addChild(KeyNode child) {
		children.add(child);
	}
}

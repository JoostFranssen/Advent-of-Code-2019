package advent18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyNode {
	private Set<Character> keysObtained;
	private Character key;
	private List<KeyNode> children;
	
	public KeyNode(Character key, Set<Character> keysObtained) {
		this.keysObtained = new HashSet<>(keysObtained);
		this.key = key;
		children = new ArrayList<>();
	}
	
	public void addChild(KeyNode child) {
		children.add(child);
	}
}

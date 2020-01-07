package advent18;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private List<Door> doors;
	private List<Key> keys;
	private int length;
	private Passage start, end;
	
	public Path(Passage start, Passage end, int length, List<Door> doors, List<Key> keys) {
		this.start = start;
		this.end = end;
		this.length = length;
		this.doors = new ArrayList<>(doors);
		this.keys = new ArrayList<>(keys);
	}
	
	public List<Key> getKeys() {
		return new ArrayList<>(keys);
	}
	
	public List<Door> getDoors() {
		return new ArrayList<>(doors);
	}

	public int getLength() {
		return length;
	}

	public Passage getStart() {
		return start;
	}

	public Passage getEnd() {
		return end;
	}
	
	@Override
	public String toString() {
		return start + " --> " + end + " in " + length + "; doors: " + doors + "; keys: " + keys;
	}
}

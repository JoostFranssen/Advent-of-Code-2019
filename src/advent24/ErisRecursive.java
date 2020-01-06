package advent24;

import java.util.HashMap;
import java.util.Map;

public class ErisRecursive extends Eris {
	private Map<Integer, ErisRecursiveState> states;
	
	public ErisRecursive(Tile[][] initialGrid) {
		super(initialGrid);
		states = new HashMap<>();
		states.put(0, new ErisRecursiveState(initialGrid));
	}
	
	@Override
	public void runIteration() {
		int min = getLowestLevel();
		if(states.get(min).countBugs() != 0) {
			ErisRecursiveState lowestLevel = new ErisRecursiveState(5);
			states.put(min - 1, lowestLevel);
			states.get(min).addAround(lowestLevel);
		}
		
		int max = getHighestLevel();
		if(states.get(max).countBugs() != 0) {
			ErisRecursiveState highestLevel = new ErisRecursiveState(5);
			states.put(max + 1, highestLevel);
			highestLevel.addAround(states.get(max));
		}
		
		for(int i : states.keySet()) {
			states.get(i).prepareUpdate();
		}
		
		for(int i : states.keySet()) {
			states.get(i).completeUpdate();
		}
	}
	
	private int getHighestLevel() {
		return states.keySet().stream().max(Integer::compare).get();
	}
	
	private int getLowestLevel() {
		return states.keySet().stream().min(Integer::compare).get();
	}
	
	public int countBugs() {
		int bugs = 0;
		for(int i : states.keySet()) {
			bugs += states.get(i).countBugs();
		}
		return bugs;
	}
	
	public void printStates() {
		for(int i : states.keySet()) {
			System.out.println("L " + i + ":");
			System.out.println(states.get(i));
		}
	}
}

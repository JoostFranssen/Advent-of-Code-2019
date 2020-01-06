package advent24;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Eris {
	private List<String> gridHistory;
	private ErisState state;
	
	public Eris(Tile[][] initialGrid) {
		gridHistory = new ArrayList<>();
		state = new ErisState(initialGrid);
		gridHistory.add(state.toString());
	}
	
	public void runIteration() {
		state.update();
		gridHistory.add(state.toString());
	}
	
	public void runUntil(BooleanSupplier condition) {
		while(!condition.getAsBoolean()) {
			runIteration();
		}
	}
	
	public String firstDuplicate() {
		for(int i = 0; i < gridHistory.size(); i++) {
			for(int j = i + 1; j < gridHistory.size(); j++) {
				if(gridHistory.get(i).equals(gridHistory.get(j))) {
					return gridHistory.get(i);
				}
			}
		}
		return null;
	}
	
	public void printHistory() {
		for(String string : gridHistory) {
			System.out.println(string);
		}
	}
}

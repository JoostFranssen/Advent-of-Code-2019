package advent16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlawedFrequencyTransmission {
	private List<Integer> signal;
	private List<Integer> basePattern = Arrays.asList(0, 1, 0, -1);
	
	public FlawedFrequencyTransmission(String input) {
		signal = Arrays.asList(input.split("")).stream().map(Integer::valueOf).collect(Collectors.toList());
	}
	
	public void performPhase() {
		List<Integer> newSignal = new ArrayList<>();
		
		for(int i = 0; i < signal.size(); i++) {
			int baseIndex = 0;
			int baseRepeat = i;
			int newValue = 0;
			for(int j = 0; j < signal.size(); j++) {
				if(baseRepeat <= 0) {
					baseRepeat = i + 1;
					baseIndex = (baseIndex + 1) % basePattern.size();
				}
				baseRepeat--;
				
				int signalValue = signal.get(j);
				int baseValue = basePattern.get(baseIndex);
				newValue += signalValue * baseValue % 10;
			}
			newSignal.add(Math.abs(newValue % 10));
		}
		signal = newSignal;
	}
	
	public List<Integer> getSignal() {
		return new ArrayList<>(signal);
	}
	
	@Override
	public String toString() {
		return String.join("", signal.stream().map(String::valueOf).toArray(String[]::new));
	}
}

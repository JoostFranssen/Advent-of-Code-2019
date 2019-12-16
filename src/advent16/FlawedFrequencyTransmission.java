package advent16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlawedFrequencyTransmission {
	private List<Integer> signal;
	private List<Integer> basePattern = Arrays.asList(0, 1, 0, -1);
	private int offset;
	
	public FlawedFrequencyTransmission(String input) {
		this(input, 0);
	}
	public FlawedFrequencyTransmission(String input, int offset) {
		signal = Arrays.asList(input.split("")).stream().map(Integer::valueOf).collect(Collectors.toList());
		this.offset = offset;
	}
	
	public void performPhase() {
		List<Integer> newSignal = new ArrayList<>();
		
		for(int i = 0; i < signal.size(); i++) {
			int baseIndex = 1;
			int index = i;
			int newValue = 0;
			
			while(index < signal.size()) {
				if(basePattern.get(baseIndex) != 0) {
					Optional<Integer> partialSum = signal.subList(index, Math.min(index + i + offset + 1, signal.size())).stream().reduce((x, y) -> x + y);
					if(partialSum.isPresent()) {
						newValue = newValue + basePattern.get(baseIndex) * partialSum.get();
//						System.out.println("i=" + i + "; index=" + index + "; basePattern=" + basePattern.get(baseIndex) + "; signalpart=" + signal.subList(index, Math.min(index + i + globalOffset + 1 - offset, signal.size())) + "; partialSum=" + partialSum.get() + "; newValue=" + newValue);
					}
				}
				baseIndex = (baseIndex + 1) % basePattern.size();
				index += i + offset + 1;
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

package advent07;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import general.InputReader;
import general.Permutation;
import intcode.Program;

public class Solution {
	private static final String FILENAME = "src/advent07/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Integer> intcode = Arrays.asList(input.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
		
		//part 1
		int maxValue = Integer.MIN_VALUE;
		Integer[] maxPermutation = null;
		for(Integer[] permutation : Permutation.getDigitPermutation(4)) {
			int value = computeAmplifiers(intcode, 0, permutation);
			if(value > maxValue) {
				maxValue = value;
				maxPermutation = permutation;
			}
		}
		
		System.out.println(String.format("%d: %s", maxValue, Arrays.toString(maxPermutation))); //65464: [0, 3, 4, 2, 1]
	}
	
	private static int computeAmplifiers(List<Integer> intcode, int startInput, Integer... phaseSettings) {
		Program program;
		int input = startInput;
		
		for(int i = 0; i < phaseSettings.length; i++) {
			program = new Program(intcode, phaseSettings[i], input);
			program.run();
			input = program.getLastOutput();
		}
		
		return input;
	}
}

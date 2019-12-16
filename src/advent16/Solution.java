package advent16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent16/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		
		FlawedFrequencyTransmission fft = new FlawedFrequencyTransmission(input);
		
		//part 1
		for(int i = 0; i < 100; i++) {
			fft.performPhase();
		}
		System.out.println(fft.toString().substring(0, 8)); //84970726
		
		//part 2
		input = input.repeat(10_000);
		int offset = Integer.parseInt(input.substring(0, 7));
		
		//to compute the n-th digit in the fft the first n-1 digits do not contribute, so we can ignore the first offset-amount
		input = input.substring(offset);
		
		//input length = 522641, while offset = 5977359; so from the repeated sequence 0, 1, 0, -1, only the 1 appears after skipping the offset
		//this means that the n-th digit is just the sum mod 10 of the following digits
		List<Integer> signal = Arrays.asList(input.split("")).stream().map(Integer::valueOf).collect(Collectors.toList());
		
		for(int i = 0; i < 100; i++) {
			for(int j = signal.size() - 2; j >= 0; j--) {
				signal.set(j, (signal.get(j) + signal.get(j + 1)) % 10);
			}
		}
		
		System.out.println(String.join("", signal.subList(0, 8).stream().map(String::valueOf).toArray(String[]::new))); //47664469
	}
}

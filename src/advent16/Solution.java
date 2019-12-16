package advent16;

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
		
		fft = new FlawedFrequencyTransmission(input);
		
		for(int i = 0; i < 100; i++) {
			fft.performPhase();
		}
		
		int skip = Integer.parseInt(fft.toString().substring(0, 7));
		System.out.println(fft.toString().substring(skip, skip + 8));
	}
}

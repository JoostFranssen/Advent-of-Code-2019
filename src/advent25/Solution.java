package advent25;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent25/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		Droid droid = new Droid(intcode);
		
		droid.run();
		
		//part 1
		/*
		 * code: 84410376
		 * with items:
		 * 
		 * spool of cat6
		 * hypercube
		 * weather machine
		 * fuel cell
		 * tambourine
		 */
	}
}

package advent19;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
		private static final String FILENAME = "src/advent19/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		TractorBeam beam = new TractorBeam(50, 50, intcode);
		System.out.println(beam.getAffectedPoints().size()); //229
		
		//part 2
	}
}

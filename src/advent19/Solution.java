package advent19;

import java.awt.Point;
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
		TractorBeam beam = new TractorBeam(intcode);
		System.out.println(beam.getAffectedPoints(50, 50).size()); //229
		
		//part 2
		Point topLeft = beam.fitSquare(100);
		System.out.println(10_000 * topLeft.x + topLeft.y); //6950903
	}
}

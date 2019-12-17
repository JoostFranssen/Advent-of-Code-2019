package advent17;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent17/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		Robot robot = new Robot(intcode);
		robot.print();
		System.out.println(robot.findIntersections());
		System.out.println(robot.sumAllAlignmentParameters());
	}
}

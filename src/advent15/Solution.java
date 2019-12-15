package advent15;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent15/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		RepairDroid droid = new RepairDroid(intcode);
		
		//part 1
		droid.run((position, output) -> output == RepairDroid.OXYGEN_TANK);
		System.out.println(droid.getSteps()); //252
		
		//part 2
		droid.run((position, output) -> position.equals(new Point(0, 0)));
		System.out.println(droid.fillWithOxygen()); //350
	}
}

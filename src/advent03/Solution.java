package advent03;

import static java.util.Arrays.asList;
import java.util.List;

import general.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent03/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<String> input = inputReader.readLines();
		
		WireGrid grid = new WireGrid();
		grid.addWire(asList(input.get(0).split(",")));
		grid.addWire(asList(input.get(1).split(",")));
		
		System.out.println(grid.getMinIntersectionDistanceToOrigin());
	}
}

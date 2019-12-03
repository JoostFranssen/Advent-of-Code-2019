package advent03;

import java.awt.Point;
import java.util.List;

import general.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent03/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<String> input = inputReader.readLines();
		
		Wire wire1 = new Wire(input.get(0));
		Wire wire2 = new Wire(input.get(1));
		
		System.out.println(wire1.getClosestIntersectionManhattan(wire2));
	}
}

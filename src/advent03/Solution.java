package advent03;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent03/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<String> input = inputReader.readLines();
		
		Wire wire1 = new Wire(input.get(0));
		Wire wire2 = new Wire(input.get(1));
		
		System.out.println(wire1.getClosestIntersectionManhattan(wire2)); //3229
		
		System.out.println(wire1.getClosestIntersectionTravelDistance(wire2)); //32132
	}
}

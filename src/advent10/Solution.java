package advent10;

import java.util.List;

import general.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent10/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<String> input = inputReader.readLines();
		
		AsteroidBelt belt = new AsteroidBelt(input);
		
		System.out.println(belt.getBestAsteroid());
	}
}

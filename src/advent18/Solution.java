package advent18;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent18/input.txt";
	
	public static void main(String[] args) {
		//part 1
		InputReader<char[]> inputReader = new InputReader<>(FILENAME);
		List<char[]> input = inputReader.readConvertLines(s -> s.toCharArray());
		
		Maze maze = new Maze(input);
		
		System.out.println(maze.findShortestDistanceToAllKeys()); //3646
		
		//part 2
		
	}
}

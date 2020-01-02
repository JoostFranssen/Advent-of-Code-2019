package advent20;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent20/input.txt";
	
	public static void main(String[] args) {
		InputReader<char[]> inputReader = new InputReader<>(FILENAME);
		List<char[]> input = inputReader.readConvertLines(s -> s.toCharArray());
		
		PlutoMaze maze = new PlutoMaze(input);
		System.out.println(maze.findShortestPathLength()); //560
		
		System.out.println(maze.findShortestPathLengthRecursive()); //6642
	}
}

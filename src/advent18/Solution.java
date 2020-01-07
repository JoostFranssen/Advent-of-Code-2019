package advent18;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent18/input.txt";
	
	public static void main(String[] args) {
		InputReader<char[]> inputReader = new InputReader<>(FILENAME);
		List<char[]> input = inputReader.readConvertLines(s -> s.toCharArray());
		
		Maze maze = new Maze(input);
		
		Key key = maze.getKeyByLabel('v');
		Passage start = maze.getStart();
		
//		maze.getPathsToReachableKeysWithoutIntermediateKeys(key, maze.getKeysByLabel(Arrays.asList('g', 'l', 'v'))).forEach(System.out::println);
		//start -> g, t, x, v
		//g -> l
		//l[g] -> t, v
		//v[g, l] ->
		
		
		System.out.println(maze.findShortestDistanceToAllKeys());
	}
}

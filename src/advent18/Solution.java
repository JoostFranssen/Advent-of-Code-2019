package advent18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent18/input.txt";
	private static final String FILENAME2 = "src/advent18/input2.txt";
	
	public static void main(String[] args) {
		//part 1
		InputReader<char[]> inputReader = new InputReader<>(FILENAME);
		List<char[]> input = inputReader.readConvertLines(s -> s.toCharArray());
		
		Maze maze = new Maze(input);
		
		System.out.println(maze.findShortestDistanceToAllKeys()); //3646
		
		//part 2
		inputReader = new InputReader<>(FILENAME2);
		input = inputReader.readConvertLines(s -> s.toCharArray());
		
		int mid = 41;
		
		List<List<char[]>> inputs = new ArrayList<List<char[]>>();
		inputs.add(input.subList(0, mid).stream().map(r -> Arrays.copyOfRange(r, mid - 1, r.length)).collect(Collectors.toList()));
		inputs.add(input.subList(0, mid).stream().map(r -> Arrays.copyOfRange(r, 0, mid)).collect(Collectors.toList()));
		inputs.add(input.subList(mid - 1, input.size()).stream().map(r -> Arrays.copyOfRange(r, 0, mid)).collect(Collectors.toList()));
		inputs.add(input.subList(mid - 1, input.size()).stream().map(r -> Arrays.copyOfRange(r, mid - 1, r.length)).collect(Collectors.toList()));
		
		for(List<char[]> quadrant : inputs) {
			List<Character> keys = new ArrayList<>();
			for(char[] line : quadrant) {
				for(char c : line) {
					if(Character.isLowerCase(c)) {
						keys.add(c);
					}
				}
			}
			for(char[] line : quadrant) {
				for(int i = 0; i < line.length; i++) {
					if(Character.isUpperCase(line[i])) {
						if(!keys.contains(Character.toLowerCase(line[i]))) {
							line[i] = '.';
						}
					}
				}
			}
		}
		
		Maze[] mazes = new Maze[inputs.size()];
		for(int i = 0; i < mazes.length; i++) {
			mazes[i] = new Maze(inputs.get(i));
		}
		
		int steps = 0;
		for(Maze mazeQuadrant : mazes) {
			steps += mazeQuadrant.findShortestDistanceToAllKeys();
		}
		System.out.println(steps); //1730
	}
}

package advent05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import general.InputReader;
import intcode.Program;

public class Solution {
	private static final String FILENAME = "src/advent05/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<Integer> intcode = Arrays.asList(inputReader.readLines().get(0).split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
		
		Program program = new Program(intcode, 1);
		System.out.println(program.getOutput());
	}
}

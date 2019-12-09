package advent09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import general.InputReader;
import intcode.Program;

public class Solution {
	private static final String FILENAME = "src/advent09/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		Program program = new Program(intcode, 1L);
		program.run();
		List<Long> output = new ArrayList<>();
		while(program.hasOutput()) {
			output.add(program.getNextOutput());
		}
		System.out.println(output); //[3280416268]
		
		program = new Program(intcode, 2L);
		program.run();
		output = new ArrayList<>();
		while(program.hasOutput()) {
			output.add(program.getNextOutput());
		}
		System.out.println(output); //[80210]
	}
}

package advent05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import intcode.Program;
import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent05/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<Long> intcode = Arrays.asList(inputReader.readLines().get(0).split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		Program program = new Program(intcode, 1L);
		program.run();
		long result;
		do {
			result = program.getNextOutput();
		} while(result == 0L);
		System.out.println(result); //12234644
		
		program = new Program(intcode, 5L);
		program.run();
		do {
			result = program.getNextOutput();
		} while(result == 0);
		System.out.println(result); //3508186
	}
}

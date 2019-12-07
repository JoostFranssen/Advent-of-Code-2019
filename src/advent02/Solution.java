package advent02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import general.InputReader;
import intcode.Program;
import intcode.ProgramStatus;

public class Solution {
	private static final String FILENAME = "src/advent02/input.txt";
	
	private static final int DESIRED_OUTPUT = 19690720;
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		
		List<Integer> intcode = Arrays.asList(input.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
		
		System.out.println(executeInputNounVerb(intcode, 12, 2)); //3101878
		
		int noun = 0;
		int verb = 0;
		boolean outputFound = false;
		
		OUTER:
		for(noun = 0; noun <= 99; noun++) {
			for(verb = 0; verb <= 99; verb++) {
				if(executeInputNounVerb(intcode, noun, verb) == DESIRED_OUTPUT) {
					outputFound = true;
					break OUTER;
				}
			}
		}
		
		if(outputFound) {
			System.out.println(100 * noun + verb); //8444
		}
	}
	
	private static int executeInputNounVerb(List<Integer> input, int noun, int verb) {
		List<Integer> intcode = new ArrayList<>(input);
		
		intcode.set(1, noun);
		intcode.set(2, verb);
		
		Program program = new Program(intcode);
		program.run();
		if(program.getStatus() == ProgramStatus.FINISHED) {
			return program.getSourceCode().get(0);
		} else {
			throw new IllegalStateException("Program did not finish properly");
		}
	}
}

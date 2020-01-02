package advent21;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import intcode.Program;
import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent21/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		Program droid = new Program(intcode);
		droid.supplyInput("NOT T J\n"
			+ "AND J T\n"
			+ "NOT T T\n"
			+ "AND A T\n"
			+ "AND B T\n"
			+ "AND C T\n"
			+ "NOT D J\n"
			+ "NOT J J\n"
			+ "NOT T T\n"
			+ "AND T J\n"
			+ "WALK\n");
		droid.run();
		while(droid.hasOutput()) {
			droid.getNextOutput();
		}
		System.out.println(droid.getLastRetrievedOutput()); //19355436
		
		//part 2
		droid = new Program(intcode);
		droid.supplyInput("NOT T J\n"
			+ "AND J T\n"
			+ "NOT T T\n"
			+ "AND A T\n"
			+ "AND B T\n"
			+ "AND C T\n"
			+ "NOT D J\n"
			+ "NOT J J\n"
			+ "NOT T T\n"
			+ "AND T J\n"
			+ "NOT H T\n"
			+ "NOT T T\n"
			+ "OR E T\n"
			+ "AND T J\n"
			+ "RUN\n");
		droid.run();
		while(droid.hasOutput()) {
			droid.getNextOutput();
		}
		System.out.println(droid.getLastRetrievedOutput()); //1142618405
	}
}

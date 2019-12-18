package advent17;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent17/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		Robot robot = new Robot(intcode);
		System.out.println(robot.sumAllAlignmentParameters()); //3292
		
		//part 2
		intcode.set(0, 2L);
		robot = new Robot(intcode);
		robot.print();
		robot.setRoutine('A', "L,12,R,4,R,4,L,6\n");
		robot.setRoutine('B', "L,12,R,4,R,4,R,12\n");
		robot.setRoutine('C', "L,10,L,6,R,4\n");
		
		//"L,12,R,4,R,4,L,6,L,12,R,4,R,4,R,12,L,12,R,4,R,4,L,6,L,10,L,6,R,4,L,12,R,4,R,4,L,6,L,12,R,4,R,4,R,12,L,10,L,6,R,4,L,12,R,4,R,4,R,12,L,10,L,6,R,4,L,12,R,4,R,4,L,6"
		
		robot.setRoutine('m', "A,B,A,C,A,B,C,A,C,A\n");
		
		robot.suppyInputRoutine(robot.getRoutine('m'));
		robot.suppyInputRoutine(robot.getRoutine('A'));
		robot.suppyInputRoutine(robot.getRoutine('B'));
		robot.suppyInputRoutine(robot.getRoutine('C'));
		robot.suppyInputRoutine(Arrays.asList((long)'y', (long)'\n'));
		
		System.out.println(robot.getOutput());
		while(robot.getProgram().hasOutput()) {
			System.out.print(robot.getOutput());
		}
	}
}

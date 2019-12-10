package advent07;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import intcode.Program;
import intcode.ProgramStatus;
import util.InputReader;
import util.Permutation;

public class Solution {
	private static final String FILENAME = "src/advent07/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		long maxValue = Long.MIN_VALUE;
		Integer[] maxPermutation = null;
		for(Integer[] permutation : Permutation.getDigitPermutation(4)) {
			long value = computeAmplifiers(intcode, 0L, permutation);
			if(value > maxValue) {
				maxValue = value;
				maxPermutation = permutation;
			}
		}
		
		System.out.println(String.format("%d: %s", maxValue, Arrays.toString(maxPermutation))); //65464: [0, 3, 4, 2, 1]
		
		//part 2
		maxValue = Integer.MIN_VALUE;
		maxPermutation = null;
		for(Integer[] permutation : Permutation.getDigitPermutation(5, 9)) {
			long value = computeAmplifiersFeedbackLoop(intcode, 0L, permutation);
			if(value > maxValue) {
				maxValue = value;
				maxPermutation = permutation;
			}
		}
		
		System.out.println(String.format("%d: %s", maxValue, Arrays.toString(maxPermutation))); //1518124: [7, 9, 5, 6, 8]
	}
	
	private static long computeAmplifiers(List<Long> intcode, long startInput, Integer... phaseSettings) {
		Program program;
		long input = startInput;
		
		for(int i = 0; i < phaseSettings.length; i++) {
			program = new Program(intcode, (long)phaseSettings[i], input);
			program.run();
			input = program.getNextOutput();
		}
		
		return input;
	}
	
	private static long computeAmplifiersFeedbackLoop(List<Long> intcode, long startInput, Integer... phaseSettings) {
		Program[] programs = new Program[phaseSettings.length];
		
		for(int i = 0; i < programs.length; i++) {
			programs[i] = new Program(intcode, (long)phaseSettings[i]);
		}
		
		programs[0].supplyInput(startInput);
		
		Thread[] threads = new Thread[programs.length];
		
		for(int i = 0; i < threads.length; i++) {
			final int j = i;
			threads[i] = new Thread(() ->  {
				int previousJ = Math.floorMod(j - 1, programs.length);
				while(programs[j].getStatus() != ProgramStatus.FINISHED) {
					programs[j].run();
					if(programs[previousJ].hasOutput()) {
						programs[j].supplyInput(programs[previousJ].getNextOutput());
					}
				}
			});
		}
		
		Arrays.asList(threads).forEach(Thread::start);
		Arrays.asList(threads).forEach(t -> {
			try {
				t.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		if(programs[programs.length - 1].getStatus() == ProgramStatus.FINISHED) {
			long result = programs[programs.length - 1].getLastRetrievedOutput();
			do {
				result = programs[programs.length - 1].getNextOutput();
			} while(programs[programs.length - 1].hasOutput());
			return result;
		}
		
		throw new NoSuchElementException("No result could be produced");
	}
}

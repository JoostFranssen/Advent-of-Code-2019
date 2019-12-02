package advent01;

import java.util.List;

import general.InputReader;

public class Solution1 {
	private static final String filename = "src/advent01/input1.txt";
	
	public static void main(String[] args) {
		InputReader<Integer> inputReader = new InputReader<>(filename, Integer::valueOf);
		List<Integer> input = inputReader.getInput();
		
		System.out.println(input.stream().map(Solution1::requiredFuel).reduce((x, y) -> x + y).get());
	}
	
	private static int requiredFuel(int mass) {
		return mass / 3 - 2;
	}
}

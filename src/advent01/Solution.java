package advent01;

import java.util.List;
import java.util.function.Function;

import general.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent01/input.txt";
	
	public static void main(String[] args) {
		InputReader<Integer> inputReader = new InputReader<>(FILENAME);
		List<Integer> input = inputReader.readConvertLines(Integer::valueOf);
		
		System.out.println(totalRequiredFuel(input, Solution::requiredFuel));
		System.out.println(totalRequiredFuel(input, Solution::requiredFuelIncludingFuel));
	}
	
	private static int requiredFuel(int mass) {
		int fuel = mass / 3 - 2;
		return Math.max(0, fuel);
	}
	
	private static int requiredFuelIncludingFuel(int mass) {
		int fuel = requiredFuel(mass);
		
		int lastFuel = fuel;
		while(lastFuel != 0) {
			lastFuel = requiredFuel(lastFuel);
			fuel += lastFuel;
		}
		
		return fuel;
	}
	
	private static int totalRequiredFuel(List<Integer> masses, Function<Integer, Integer> massToFuel) {
		return masses.stream().map(massToFuel).reduce((x, y) -> x + y).get();
	}
}

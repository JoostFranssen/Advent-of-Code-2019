package advent12;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent12/input.txt";
	
	public static void main(String[] args) {
		InputReader<Moon> inputReader = new InputReader<>(FILENAME);
		List<Moon> moons = inputReader.readConvertLines(s -> new Moon(s));
		
		for(int i = 0; i < 1000; i++) {
			performStep(moons);
		}
		
		System.out.println(moons.stream().map(m -> m.getTotalEnergy()).reduce((x, y) -> x + y).get()); //10189
	}
	
	public static void performStep(List<Moon> moons) {
		for(Moon moon : moons) {
			for(Moon other : moons) {
				if(other == moon) {
					continue;
				}
				moon.applyGravity(other);
			}
		}
		moons.forEach(m -> m.applySpeed());
	}
}

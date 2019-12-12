package advent12;

import java.util.List;

import util.InputReader;
import util.MathExt;

public class Solution {
	private static final String FILENAME = "src/advent12/input.txt";
	
	public static void main(String[] args) {
		InputReader<Moon> inputReader = new InputReader<>(FILENAME);
		List<Moon> moons = inputReader.readConvertLines(s -> new Moon(s));
		
		//part 1
		for(int i = 0; i < 1000; i++) {
			performStep(moons);
		}
		System.out.println(moons.stream().map(m -> m.getTotalEnergy()).reduce((x, y) -> x + y).get()); //10189
		
		//part 2
		moons = inputReader.readConvertLines(s -> new Moon(s));
		long xFixIndex = 0;
		long yFixIndex = 0;
		long zFixIndex = 0;
		boolean foundX = false;
		boolean foundY = false;
		boolean foundZ = false;
		do {
			performStep(moons);
			if(!foundX) {
				xFixIndex++;
				foundX = moons.stream().allMatch(m -> m.isInitialXState());
			}
			if(!foundY) {
				yFixIndex++;
				foundY = moons.stream().allMatch(m -> m.isInitialYState());
			}
			if(!foundZ) {
				zFixIndex++;
				foundZ =  moons.stream().allMatch(m -> m.isInitialZState());
			}
		} while(!(foundX && foundY && foundZ));
		System.out.println(MathExt.lcm(xFixIndex, MathExt.lcm(yFixIndex, zFixIndex))); //469671086427712
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

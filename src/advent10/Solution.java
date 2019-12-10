package advent10;

import java.util.ArrayList;
import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent10/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		List<String> input = inputReader.readLines();
		
		AsteroidBelt belt = new AsteroidBelt(input);
		
		//part 1
		Asteroid bestAsteroid = belt.getBestAsteroid();
		System.out.println(bestAsteroid.getVisibleAsteroidCount()); //282
		
		//part 2
		
		List<Asteroid> vaporizedAsteroids = new ArrayList<>();
		while(vaporizedAsteroids.size() < 200) {
			List<Asteroid> toBeVaporized = belt.getVisibleAsteroidsInOrderOfRotation(bestAsteroid);
			vaporizedAsteroids.addAll(toBeVaporized);
			belt.vaporizeAsteroids(toBeVaporized);
			break;
		}
		
		Asteroid vaporized200 = vaporizedAsteroids.get(199);
		System.out.println(100 * vaporized200.x + vaporized200.y); //1008
	}
}

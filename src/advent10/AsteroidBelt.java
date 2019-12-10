package advent10;

import java.util.ArrayList;
import java.util.List;

public class AsteroidBelt {
	List<Asteroid> asteroids;
	
	public AsteroidBelt(List<String> input) {
		asteroids = new ArrayList<>();
		for(int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			for(int j = 0; j < line.length(); j++) {
				if(line.charAt(j) == '#') {
					asteroids.add(new Asteroid(j, i));
				}
			}
		}
	}
	
	public Asteroid getBestAsteroid() {
		for(Asteroid asteroid : asteroids) {
			countVisibleAsteroidsFrom(asteroid);
		}
		
		return asteroids.stream().max((a1, a2) -> Integer.compare(a1.getVisibleAsteroidCount(), a2.getVisibleAsteroidCount())).get();
	}
	
	public void vaporizeAsteroids(List<Asteroid> toBeVaporized) {
		asteroids.removeAll(toBeVaporized);
		asteroids.forEach(a -> a.clearAllVisibleAsteroids());
	}
	
	public void countVisibleAsteroidsFrom(Asteroid asteroid) {
		TARGET_LOOP:
		for(Asteroid target : asteroids) {
			if(asteroid.equals(target)) {
				continue;
			}
			for(Asteroid other : asteroids) {
				if(other.equals(target) || other.equals(asteroid)) {
					continue;
				}
				if(other.onLineSegment(asteroid, target)) {
					continue TARGET_LOOP;
				}
			}
			asteroid.addAsteroid(target);
		}
	}
	
	public List<Asteroid> getVisibleAsteroidsInOrderOfRotation(Asteroid asteroid) {
		List<Asteroid> visibles = new ArrayList<Asteroid>();
		TARGET_LOOP:
		for(Asteroid target : asteroids) {
			if(asteroid.equals(target)) {
				continue;
			}
			for(Asteroid other : asteroids) {
				if(other.equals(target) || other.equals(asteroid)) {
					continue;
				}
				if(other.onLineSegment(asteroid, target)) {
					continue TARGET_LOOP;
				}
			}
			visibles.add(target);
		}
		visibles.sort((a1, a2) -> (int)Math.signum(asteroid.angleBetween(a1) - asteroid.angleBetween(a2)));
		return visibles;
	}
}

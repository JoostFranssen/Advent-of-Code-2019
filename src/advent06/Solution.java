package advent06;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent06/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> reader = new InputReader<>(FILENAME);
		List<String> input = reader.readLines();
		
		Map<String, Body> bodies = new HashMap<>();
		for(String line : input) {
			String[] bodyNames = line.split("\\)");
			for(String name : bodyNames) {
				if(!bodies.containsKey(name)) {
					bodies.put(name, new Body());
				}
			}
		}
		for(String line : input) {
			String[] bodyNames = line.split("\\)");
			Body orbitedBody = bodies.get(bodyNames[0]);
			Body orbitingBody = bodies.get(bodyNames[1]);
			orbitingBody.setOrbitsAround(orbitedBody);
		}
		
		//part 1
		int count = 0;
		for(String name : bodies.keySet()) {
			count += bodies.get(name).countOrbits();
		}
		
		System.out.println(count); //453028
		
		//part 2
		Body you = bodies.get("YOU");
		Body yourOrbit = you.getOrbitedBody();
		Body target = bodies.get("SAN").getOrbitedBody();
		
		System.out.println(yourOrbit.getFewestConnectionsTo(target, you).get()); //562
	}
}

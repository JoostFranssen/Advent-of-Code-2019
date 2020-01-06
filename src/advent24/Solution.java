package advent24;

import java.util.Arrays;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent24/input.txt";
	
	public static void main(String[] args) {
		InputReader<Tile[]> inputReader = new InputReader<>(FILENAME);
		Tile[][] grid = inputReader.readConvertLines(s -> Arrays.asList(s.split("")).stream().map(c -> new Tile(c.equals(Character.toString(Tile.BUG_CHAR)))).toArray(Tile[]::new)).toArray(Tile[][]::new);
		
		//part 1
		final Eris eris = new Eris(grid);
		
		eris.runUntil(() -> eris.firstDuplicate() != null);
		System.out.println(ErisState.biodiversityRatingFromString(eris.firstDuplicate())); //32509983
		
		//part 2
		ErisRecursive erisRecursive = new ErisRecursive(grid);

		erisRecursive.runFor(200);
		
		System.out.println(erisRecursive.countBugs()); //2012
	}
}

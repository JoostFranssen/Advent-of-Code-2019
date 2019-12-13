package advent13;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent13/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		Game game = new Game(intcode);
		game.run();
		
		//part 1
		System.out.println(game.countTiles(Game.TILE_BLOCK)); //213
		
		//part 2
		intcode.set(0, 2L);
		game = new Game(intcode);
		game.run();
		
		game.playToFinish();
		
		System.out.println(game.getScore()); //11441
	}
}

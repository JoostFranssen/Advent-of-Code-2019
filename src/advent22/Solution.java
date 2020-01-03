package advent22;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent22/input.txt";
	private static final long DECK_SIZE = 119_315_717_514_047L;
	private static final long SHUFFLE_COUNT = 101_741_582_076_661L;
	
	public static void main(String[] args) {
		InputReader<Shuffle> inputReader = new InputReader<>(FILENAME);
		List<Shuffle> shuffles = inputReader.readConvertLines(s -> Shuffle.shuffleFromString(s));
		
		//part 1
		SpaceDeck deck = new SpaceDeck();
		
		for(Shuffle shuffle : shuffles) {
			shuffle.perform(deck);
		}
		
		System.out.println(deck.getCardPosition(2019)); //4096
		
		//part 2
		InputReader<LinearModuloMap> linearInputReader = new InputReader<>(FILENAME);
		List<LinearModuloMap> linearShuffles = linearInputReader.readConvertLines(s -> LinearModuloMap.mapFromShuffleStringInversePosition(s, DECK_SIZE));
		
		LinearModuloMap iteration = LinearModuloMap.identity(DECK_SIZE);
		for(LinearModuloMap shuffle : linearShuffles) {
			iteration = iteration.precompose(shuffle);
		}
		
		iteration = iteration.composeSelf(SHUFFLE_COUNT);
		
		System.out.println(iteration.evaluate(2020L)); //78613970589919
	}
}

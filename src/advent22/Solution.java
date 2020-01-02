package advent22;

import java.util.List;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent22/input.txt";
	
	public static void main(String[] args) {
		InputReader<Shuffle> inputReader = new InputReader<>(FILENAME);
		List<Shuffle> shuffles = inputReader.readConvertLines(s -> Shuffle.shuffleFromString(s));
		
		SpaceDeck deck = new SpaceDeck();
		
		for(Shuffle shuffle : shuffles) {
			shuffle.perform(deck);
		}
		
		System.out.println(deck.getCardPosition(2019)); //4096
	}
}

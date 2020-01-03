package advent22;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SpaceDeck implements Shuffleable {
	public static final int DEFAULT_SIZE = 10_007;
	
	private Stack<Integer> deck;
	private int size;
	
	public SpaceDeck() {
		this(DEFAULT_SIZE);
	}
	public SpaceDeck(int size) {
		this.size = size;
		deck = new Stack<>();
		for(int i = size - 1; i >= 0; i--) {
			deck.push(i);
		}
	}
	
	@Override
	public void dealIntoNewStack() {
		Stack<Integer> newDeck = new Stack<>();
		while(!deck.isEmpty()) {
			newDeck.push(deck.pop());
		}
		deck = newDeck;
	}
	
	@Override
	public void cutCards(int n) {
		n = Math.floorMod(n, size);
		List<Integer> cutCards = deck.subList(deck.size() - n, deck.size());
		List<Integer> remainingCards = deck.subList(0, deck.size() - n);
		cutCards.addAll(remainingCards);
		deck = new Stack<>();
		deck.addAll(cutCards);
	}
	
	@Override
	public void dealWithIncrement(int n) {
		Integer[] newDeck = new Integer[size];
		int position = 0;
		while(!deck.isEmpty()) {
			newDeck[position] = deck.pop();
			position = (position + n) % size;
		}
		deck = new Stack<>();
		deck.addAll(Arrays.asList(newDeck));
		dealIntoNewStack();
	}
	
	public int getCardPosition(int cardNumber) {
		int index = deck.indexOf(cardNumber);
		if(index == -1) {
			return -1;
		} else {
			return size - 1 - index;
		}
	}
	
	public int getCardAt(int position) {
		return deck.get(size - 1 - position);
	}
	
	@Override
	public String toString() {
		return "[" + deck.stream().map(c -> c.toString()).reduce((s1, s2) -> s2 + ", " + s1).get() + "]";
	}
}

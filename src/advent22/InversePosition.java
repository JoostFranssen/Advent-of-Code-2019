package advent22;

import util.MathExt;

public class InversePosition implements Shuffleable {
	private long position;
	private long deckSize;
	
	public InversePosition(Long position, Long deckSize) {
		this.position = position;
		this.deckSize = deckSize;
	}
	
	@Override
	public void dealIntoNewStack() {
		position = deckSize - 1 - position;
	}
	
	
	public void cutCardsNormal(int n) {
		position = Math.floorMod(position - n, deckSize);
	}
	
	@Override
	public void cutCards(int n) {
		cutCardsNormal(-n);
	}
	
	
	public void dealWithIncrementNormal(int n) {
		position = Math.floorMod(position * n, deckSize);
	}
	
	@Override
	public void dealWithIncrement(int n) {
		position = Math.floorMod(position * MathExt.primeModuloInverse(n, deckSize), deckSize);
	}
	
	public long getPosition() {
		return position;
	}
}

package advent22;

@FunctionalInterface
public interface Shuffle {
	void perform(Shuffleable deck);
	
	static Shuffle shuffleFromString(String shuffleString) {
		if(shuffleString.equals("deal into new stack")) {
			return d -> d.dealIntoNewStack();
		}
		
		if(shuffleString.startsWith("deal with increment ")) {
			return d -> d.dealWithIncrement(Integer.parseInt(shuffleString.substring("deal with increment ".length())));
		}
		
		if(shuffleString.startsWith("cut ")) {
			return d -> d.cutCards(Integer.parseInt(shuffleString.substring(4)));
		}
		
		return null;
	}
}

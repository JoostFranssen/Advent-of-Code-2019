package advent18;

public class Key extends LabeledPassage {
	public Key(char label) {
		super(label);
		if(!Character.isLowerCase(label)) {
			throw new IllegalArgumentException();
		}
	}
}

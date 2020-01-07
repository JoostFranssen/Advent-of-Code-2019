package advent18;

public class Door extends LabeledPassage {
	public Door(char label) {
		super(label);
		if(!Character.isUpperCase(label)) {
			throw new IllegalArgumentException();
		}
	}
}

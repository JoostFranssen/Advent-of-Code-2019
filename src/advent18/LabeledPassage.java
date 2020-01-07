package advent18;

public abstract class LabeledPassage extends Passage {
	protected char label;
	
	public LabeledPassage(char label) {
		super();
		this.label = label;
	}
	
	public char getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return super.toString() + "." + label;
	}
}

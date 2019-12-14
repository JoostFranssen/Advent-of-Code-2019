package advent14;

public class Chemical {
	private String name;
	private int quantity;
	
	public Chemical(String data) {
		String[] split = data.split(" ");
		quantity = Integer.parseInt(split[0]);
		name = split[1];
	}
	public Chemical(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	public Chemical(Chemical chemical) {
		this(chemical.getName(), chemical.getQuantity());
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString() {
		return quantity + "×" + name;
	}
}

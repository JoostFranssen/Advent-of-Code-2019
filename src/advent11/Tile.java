package advent11;

import java.awt.Point;

public class Tile extends Point {
	private static final long serialVersionUID = -7405806124010917980L;
	
	private long color;
	
	public Tile(int x, int y, long color) {
		super(x, y);
		this.color = color;
	}
	
	public boolean paint(long newColor) {
		long oldColor = this.color;
		this.color = newColor;
		return oldColor != newColor;
	}
	
	public long getColor() {
		return color;
	}
}

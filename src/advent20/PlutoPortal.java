package advent20;

import java.awt.Point;

public class PlutoPortal extends PlutoPassage {
	private boolean outer;
	
	public PlutoPortal(Point location, boolean outer) {
		super(location);
		this.outer = outer;
	}
	
	public boolean isOuter() {
		return outer;
	}
}

package advent23;

public class Packet {
	private int destination;
	private long x, y;

	public Packet(int destination, long x, long y) {
		this.destination = destination;
		this.x = x;
		this.y = y;
	}

	public int getDestination() {
		return destination;
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}
}

package advent12;

public class Moon {
	private int x, y, z;
	private int xSpeed, ySpeed, zSpeed;
	
	public Moon(String input) {
		int[] values = new int[3];
		int index = 0;
		
		boolean append = false;
		String digitString = "";
		for(char c : input.toCharArray()) {
			if(c == '=') {
				append = true;
				continue;
			}
			if(c == ',' || c == '>') {
				append = false;
				values[index] = Integer.parseInt(digitString);
				index++;
				digitString = "";
			}
			if(append) {
				digitString += c;
				continue;
			}
		}
		x = values[0];
		y = values[1];
		z = values[2];
	}
	public Moon(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void applyGravity(Moon other) {
		xSpeed += Math.signum(other.x - x);
		ySpeed += Math.signum(other.y - y);
		zSpeed += Math.signum(other.z - z);
	}
	
	public void applySpeed() {
		x += xSpeed;
		y += ySpeed;
		z += zSpeed;
	}
	
	public int getPotentialEnergy() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}
	
	public int getKineticEnergy() {
		return Math.abs(xSpeed) + Math.abs(ySpeed) + Math.abs(zSpeed);
	}
	
	public int getTotalEnergy() {
		return getPotentialEnergy() * getKineticEnergy();
	}
	
	@Override
	public String toString() {
		return String.format("pos=<x=%d, y=%d, z=%d>, vel=<x=%d, y=%d, z=%d>", x, y, z, xSpeed, ySpeed, zSpeed);
	}
}

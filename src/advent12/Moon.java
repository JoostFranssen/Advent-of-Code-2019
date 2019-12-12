package advent12;

public class Moon {
	private int x, y, z;
	private int xSpeed, ySpeed, zSpeed;
	private final int initialX, initialY, initialZ;
	
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
		initialX = x;
		initialY = y;
		initialZ = z;
	}
	public Moon(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		initialX = x;
		initialY = y;
		initialZ = z;
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
	
	public boolean isInInitialState() {
		return isInitialXState() && isInitialYState() && isInitialZState();
	}
	
	public boolean isInitialXState() {
		return x == initialX && xSpeed == 0;
	}
	
	public boolean isInitialYState() {
		return y == initialY && ySpeed == 0;
	}
	
	public boolean isInitialZState() {
		return z == initialZ && zSpeed == 0;
	}
	
	@Override
	public String toString() {
		return String.format("pos=<x=%d, y=%d, z=%d>, vel=<x=%d, y=%d, z=%d>", x, y, z, xSpeed, ySpeed, zSpeed);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + xSpeed;
		result = prime * result + y;
		result = prime * result + ySpeed;
		result = prime * result + z;
		result = prime * result + zSpeed;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Moon other = (Moon)obj;
		if(x != other.x) {
			return false;
		}
		if(xSpeed != other.xSpeed) {
			return false;
		}
		if(y != other.y) {
			return false;
		}
		if(ySpeed != other.ySpeed) {
			return false;
		}
		if(z != other.z) {
			return false;
		}
		if(zSpeed != other.zSpeed) {
			return false;
		}
		return true;
	}
}

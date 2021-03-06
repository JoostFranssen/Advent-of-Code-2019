package advent24;

import java.util.Arrays;

public class ErisState {
	protected Tile[][] grid;
	
	public ErisState(Tile[][] initialGrid) {
		grid = new Tile[initialGrid.length][];
		for(int i = 0; i < grid.length; i++) {
			grid[i] = new Tile[initialGrid[i].length];
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Tile(initialGrid[i][j]);
			}
		}
		
		setNeighbors();
	}
	
	protected void setNeighbors() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(i > 0) {
					grid[i][j].addNeighbor(grid[i - 1][j]);
				}
				if(i + 1 < grid.length) {
					grid[i][j].addNeighbor(grid[i + 1][j]);
				}
				if(j > 0) {
					grid[i][j].addNeighbor(grid[i][j - 1]);
				}
				if(j + 1 < grid[i].length) {
					grid[i][j].addNeighbor(grid[i][j + 1]);
				}
			}
		}
	}
	
	public Tile get(int i, int j) {
		return grid[i][j];
	}
	
	public void update() {
		prepareUpdate();
		completeUpdate();
	}

	public void completeUpdate() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j].update();
			}
		}
	}

	public void prepareUpdate() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j].determineNextState();
			}
		}
	}
	
	public static long biodiversityRatingFromString(String string) {
		string = string.replaceAll("\n", "");
		long rating = 0;
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) == Tile.BUG_CHAR) {
				rating += Math.pow(2, i);
			}
		}
		return rating;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				sb.append(grid[i][j].hasBug() ? Tile.BUG_CHAR : Tile.EMPTY_CHAR);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(grid);
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
		ErisState other = (ErisState)obj;
		if(!grid.toString().equals(other.toString())) {
			return false;
		}
		return true;
	}
}

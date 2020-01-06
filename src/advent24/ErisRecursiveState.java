package advent24;

public class ErisRecursiveState extends ErisState {
	
	public ErisRecursiveState(int size) {
		this(getEmptyGrid(size));
	}
	public ErisRecursiveState(Tile[][] initialGrid) {
		super(initialGrid);
	}
	
	private static Tile[][] getEmptyGrid(int size) {
		Tile[][] grid = new Tile[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				grid[i][j] = new Tile();
			}
		}
		return grid;
	}
	
	@Override
	protected void setNeighbors() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(i == 2 && j == 2) {
					continue;
				}
				if(i > 0) {
					if(!(i - 1 == 2 && j == 2)) {
						grid[i][j].addNeighbor(grid[i - 1][j]);
					}
				}
				if(i + 1 < grid.length) {
					if(!(i + 1 == 2 && j == 2)) {
						grid[i][j].addNeighbor(grid[i + 1][j]);
					}
				}
				if(j > 0) {
					if(!(i == 2 && j - 1 == 2)) {
						grid[i][j].addNeighbor(grid[i][j - 1]);
					}
				}
				if(j + 1 < grid[i].length) {
					if(!(i == 2 && j + 1 == 2)) {
						grid[i][j].addNeighbor(grid[i][j + 1]);
					}
				}
			}
		}
	}
	
	public void addAround(ErisState surroundingState) {
		for(int j = 0; j < grid[0].length; j++) {
			grid[0][j].addNeighbor(surroundingState.get(1, 2));
			surroundingState.get(1, 2).addNeighbor(grid[0][j]);
			
			grid[4][j].addNeighbor(surroundingState.get(3, 2));
			surroundingState.get(3, 2).addNeighbor(grid[4][j]);
			
			grid[j][0].addNeighbor(surroundingState.get(2, 1));
			surroundingState.get(2, 1).addNeighbor(grid[j][0]);
			
			grid[j][4].addNeighbor(surroundingState.get(2, 3));
			surroundingState.get(2, 3).addNeighbor(grid[j][4]);
		}
	}
	
	public int countBugs() {
		int bugs = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(i == 2 && j == 2) {
					continue;
				}
				if(grid[i][j].hasBug()) {
					bugs++;
				}
			}
		}
		return bugs;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(i == 2 && j == 2) {
					sb.append("?");
				} else {
					sb.append(grid[i][j].hasBug() ? Tile.BUG_CHAR : Tile.EMPTY_CHAR);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

package advent11;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent11/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		//part 1
		HullRobot robot = new HullRobot(intcode);
		robot.run();
		System.out.println(robot.getPaintedTilesCount()); //2319
		
		//part 2
		robot = new HullRobot(intcode, new Tile(0, 0, 1L));
		robot.run();
		Collection<Tile> tiles = robot.getTiles();
		int minX = tiles.stream().map(t -> t.x).min(Integer::compare).get();
		int minY = tiles.stream().map(t -> t.y).min(Integer::compare).get();
		int maxX = tiles.stream().map(t -> t.x).max(Integer::compare).get();
		int maxY = tiles.stream().map(t -> t.y).max(Integer::compare).get();
		
		long[][] picture = new long[maxY - minY + 1][maxX - minX + 1];
		for(Tile tile : tiles) {
			picture[tile.y - minY][tile.x - minX] = tile.getColor();
		}
		
		StringBuilder result = new StringBuilder();
		for(int y = picture.length - 1; y >= 0; y--) {
			for(int x = 0; x < picture[y].length; x++) {
				result.append(picture[y][x] == 1L ? "█" : " ");
			}
			result.append("\n");
		}
		
		System.out.println(result.toString()); //UERPRFGJ
	}
}

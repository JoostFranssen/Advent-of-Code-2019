package advent08;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent08/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Integer> imageData = Arrays.asList(input.split("")).stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
		
		Image image = new Image(new Dimension(25, 6), imageData);
		
		List<ImageLayer> layers = image.getLayers();
		ImageLayer leastZeros = layers.stream().min((l1, l2) -> Integer.compare(l1.count(0), l2.count(0))).get();
		
		System.out.println(leastZeros.count(1) * leastZeros.count(2)); //1330
		
		ImageLayer compressedImage = image.compressLayers();
		
		System.out.println(compressedImage); //FAHEF
	}
}

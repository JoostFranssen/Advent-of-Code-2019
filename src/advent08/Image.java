package advent08;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Image {
	public static final Integer BLACK = 0;
	public static final Integer WHITE = 1;
	public static final Integer TRANSPARANT = 2;
	
	private Dimension dimension;
	private List<ImageLayer> layers;
	
	public Image(Dimension dimension, List<Integer> imageData) {
		this.dimension = dimension;
		layers = new ArrayList<>();
		createLayers(imageData);
	}
	
	private void createLayers(List<Integer> imageData) {
		ImageLayer currentLayer = new ImageLayer(dimension);
		
		for(Integer digit : imageData) {
			currentLayer.appendImage(digit);
			
			if(currentLayer.isComplete()) {
				layers.add(currentLayer);
				currentLayer = new ImageLayer(dimension);
			}
		}
	}
	
	public List<ImageLayer> getLayers() {
		return new ArrayList<>(layers);
	}
	
	public ImageLayer compressLayers() {
		ImageLayer compressed = new ImageLayer(dimension);
		
		while(!compressed.isComplete()) {
			compressed.appendImage(getPixelAt(compressed.getLastBlankPixelPosition()));
		}
		
		return compressed;
	}
	
	public Integer getPixelAt(Point position) {
		for(ImageLayer layer : layers) {
			if(layer.getPixel(position) == TRANSPARANT) {
				continue;
			}
			return layer.getPixel(position);
		}
		return TRANSPARANT;
	}
}

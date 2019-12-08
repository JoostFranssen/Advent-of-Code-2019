package advent08;

import java.awt.Dimension;
import java.awt.Point;

public class ImageLayer {
	private Integer[][] pixels;
	private Dimension dimension;
	private Point lastBlankPixel;
	
	public ImageLayer(Dimension dimension) {
		this(dimension, new Integer[dimension.height][dimension.width]);
		lastBlankPixel = new Point(0, 0);
	}
	public ImageLayer(Dimension dimension, Integer[][] pixels) {
		this.dimension = dimension;
		this.pixels = pixels;
	}
	
	public boolean isComplete() {
		return lastBlankPixel == null;
	}
	
	public void appendImage(Integer pixel) {
		pixels[lastBlankPixel.y][lastBlankPixel.x] = pixel;
		moveToNextPixel();
	}
	
	private void moveToNextPixel() {
		if(lastBlankPixel.x < dimension.width - 1) {
			lastBlankPixel.x++;
		} else {
			if(lastBlankPixel.y < dimension.height - 1) {
				lastBlankPixel.x = 0;
				lastBlankPixel.y++;
			} else {
				lastBlankPixel = null;
			}
		}
	}
	
	public Integer getPixel(Point position) {
		return pixels[position.y][position.x];
	}
	
	public Point getLastBlankPixelPosition() {
		return new Point(lastBlankPixel);
	}
	
	public int count(Integer digit) {
		int count = 0;
		for(Integer[] row : pixels) {
			for(Integer pixel : row) {
				count += pixel == digit ? 1 : 0;
			}
		}
		return count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int y = 0; y < dimension.height; y++) {
			for(int x = 0; x < dimension.width; x++) {
				if(pixels[y][x] == Image.WHITE) {
					sb.append("█");
				}
				if(pixels[y][x] == Image.BLACK) {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}
}

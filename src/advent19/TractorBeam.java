package advent19;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import intcode.Program;

public class TractorBeam {
	private int width, height;
	private List<Long> sourceCode;
	public TractorBeam(int width, int height, List<Long> sourceCode) {
		this.width = width;
		this.height = height;
		this.sourceCode = sourceCode;
	}
	
	public boolean checkPoint(Point p) {
		Program program = new Program(sourceCode);
		program.supplyInput((long)p.x);
		program.supplyInput((long)p.y);
		program.run();
		return program.getNextOutput() == 1L;
	}
	
	public List<Point> getAffectedPoints() {
		List<Point> points = new ArrayList<>();
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Point p = new Point(x, y);
				if(checkPoint(p)) {
					points.add(p);
				}
			}
		}
		return points;
	}
}

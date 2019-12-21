package advent19;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import intcode.Program;

public class TractorBeam {
	private List<Long> sourceCode;
	public TractorBeam(List<Long> sourceCode) {
		this.sourceCode = new ArrayList<>(sourceCode);
	}
	
	public boolean checkPoint(Point p) {
		Program program = new Program(new ArrayList<>(sourceCode));
		program.supplyInput((long)p.x);
		program.supplyInput((long)p.y);
		program.run();
		return program.getNextOutput() == 1L;
	}
	
	public List<Point> getAffectedPoints(int width, int height) {
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
	
	public Point fitSquare(int size) {
		int y = 4;
		while(true) {
			int maxX = getBeamMaxX(y);
			if(maxX - size + 1 < 3) {
				y++;
				continue;
			}
			Point topLeft = new Point(maxX - size + 1, y);
			if(checkPoint(topLeft)) {
				Point bottomLeft = new Point(topLeft.x, topLeft.y + size - 1);
				if(checkPoint(bottomLeft)) {
					return topLeft;
				}
			}
			y++;
		}
	}
	
	public int getBeamMinX(int y) {
		int x = 3;
		while(!checkPoint(new Point(x, y))) {
			x++;
		}
		return x;
	}
	
	public int getBeamMaxX(int y) {
		int x = getBeamMinX(y);
		while(checkPoint(new Point(x, y))) {
			x++;
		}
		
		return x - 1;
	}
	
	public int getBeamMinY(int x) {
		int y = 4;
		while(!checkPoint(new Point(x, y))) {
			y++;
		}
		return y;
	}
	
	public int getBeamMaxY(int x) {
		int y = getBeamMinX(x);
		while(checkPoint(new Point(x, y))) {
			y++;
		}
		
		return y - 1;
	}
	
	public void print(int width, int height) {
		List<Point> points = getAffectedPoints(width, height);
		StringBuilder sb = new StringBuilder();
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				sb.append(points.contains(new Point(x, y)) ? "#" : ".");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}

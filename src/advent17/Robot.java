package advent17;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import intcode.Program;

public class Robot {
	private Program program;
	String[] scaffolding;
	
	public Robot(List<Long> sourceCode) {
		program = new Program(sourceCode);
		run();
	}
	
	private void run() {
		program.run();
		StringBuilder sb = new StringBuilder();
		while(program.hasOutput()) {
			sb.append((char)program.getNextOutput().intValue());
		}
		scaffolding = sb.toString().split("\n");
	}
	
	public List<Point> findIntersections() {
		List<Point> intersections = new ArrayList<>();
		for(int y = 0; y < scaffolding.length; y++) {
			for(int x = 0; x < scaffolding[y].length();x ++) {
				Point p = new Point(x, y);
				if(isIntersection(p)) {
					intersections.add(p);
				}
			}
		}
		return intersections;
	}
	
	private boolean isIntersection(Point p) {
		boolean isIntersection = charAtScaffolding(p) == '#';
		for(int i = -1; i <= 1; i += 2) {
			try {
				isIntersection &= charAtScaffolding(new Point(p.x + i, p.y)) == '#';
			} catch(IndexOutOfBoundsException e) {}
			try {
				isIntersection &= charAtScaffolding(new Point(p.x, p.y + i)) == '#';
			} catch(IndexOutOfBoundsException e) {}
		}
		return isIntersection;
	}
	
	private char charAtScaffolding(Point p) {
		return scaffolding[p.y].charAt(p.x);
	}
	
	private int calcAlignmentParameters(Point intersection) {
		return intersection.x * intersection.y;
	}
	
	public int sumAllAlignmentParameters() {
		return findIntersections().stream().map(p -> calcAlignmentParameters(p)).reduce(Integer::sum).get();
	}
	
	public void print() {
		System.out.println(String.join("\n", scaffolding));
	}
}

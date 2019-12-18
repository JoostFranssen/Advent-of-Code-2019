package advent17;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import intcode.Program;

public class Robot {
	private Program program;
	String[] scaffolding;
	private Map<Character, List<Long>> routines;
	
	public Robot(List<Long> sourceCode) {
		program = new Program(sourceCode);
		routines = new HashMap<>();
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
	
	public void suppyInputRoutine(List<Long> input) {
		input.forEach(i -> program.supplyInput(i));
		program.run();
	}
	
	public void setRoutine(char name, String sequence) {
		routines.put(name, Arrays.asList(sequence.split("")).stream().map(s -> (long)s.charAt(0)).collect(Collectors.toList()));
	}
	
	public List<Long> getRoutine(char name) {
		return routines.get(name);
	}
	
	public Long getOutput() {
		return program.getNextOutput();
	}
	
	public Program getProgram() {
		return program;
	}
}

package advent03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Wire {
	private List<WireDirection> directions;
	
	public Wire(String directionsString) {
		directions = Arrays.asList(directionsString.split(",")).stream().map(d -> new WireDirection(d)).collect(Collectors.toList());
	}
	
	public List<Point> getIntersections(Wire other) {
		List<Point> intersections = new ArrayList<>();
		
		Point point = new Point(0, 0);
		for(WireDirection direction : directions) {
			Point otherPoint = new Point(0,0);
			for(WireDirection otherDirection : other.directions) {
				Point intersection = direction.intersectsFrom(point, otherDirection, otherPoint);
				if(intersection != null) {
					intersections.add(intersection);
				}
				otherPoint = otherDirection.getTranslation().apply(otherPoint);
			}
			point = direction.getTranslation().apply(point);
		}
		
		return intersections;
	}
	
	public int getClosestIntersectionManhattan(Wire other) {
		return getIntersections(other).stream().filter(p -> !(p.x == 0 && p.y == 0)).map(i -> manhattenDistance(i)).min(Integer::compare).get();
	}
	
	public int getTravelDistanceTo(Point target) {
		int distance = 0;
		
		Point start = new Point(0, 0);
		for(WireDirection direction : directions) {
			if(direction.containsPointFrom(start, target)) {
				distance += manhattenDistance(start, target);
				break;
			}
			distance += direction.distance;
			start = direction.getTranslation().apply(start);
		}
		
		return distance;
	}
	
	public int getClosestIntersectionTravelDistance(Wire other) {
		return getIntersections(other).stream().filter(p -> !(p.x == 0 && p.y == 0)).map(i -> getTravelDistanceTo(i) + other.getTravelDistanceTo(i)).min(Integer::compare).get();
	}
	
	public static int manhattenDistance(Point point) {
		return manhattenDistance(point, new Point(0, 0));
	}
	public static int manhattenDistance(Point point1, Point point2) {
		return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
	}
}

class WireDirection {
	char direction; //U, D, L R
	int distance;
	
	WireDirection(String directionString) {
		direction = directionString.charAt(0);
		distance = Integer.parseInt(directionString.substring(1));
	}
	WireDirection(char direction, int distance) {
		this.direction = direction;
		this.distance = distance;
	}
	
	Function<Point, Point> getTranslation() {
		int xTranslate = direction == 'R' ? 1 : direction == 'L' ? -1 : 0;
		int yTranslate = direction == 'U' ? 1 : direction == 'D' ? -1 : 0;
		
		return p -> new Point(p.x + xTranslate * distance, p.y + yTranslate * distance);
	}
	
	boolean containsPointFrom(Point start, Point target) {
		Point end = getTranslation().apply(start);
		
		boolean xBetween = (Math.min(start.x, end.x) <= target.x && target.x <= Math.max(start.x, end.x));
		boolean yBetween = (Math.min(start.y, end.y) <= target.y && target.y <= Math.max(start.y, end.y));
		
		return xBetween && yBetween;
	}
	
	Point intersectsFrom(Point start, WireDirection other, Point otherStart) {
		WireDirection unitDirection = new WireDirection(other.direction, 1);
		Function<Point, Point> translate = unitDirection.getTranslation();
		
		Point point = new Point(otherStart);
		for(int i = 0; i < other.distance; i++) {
			if(containsPointFrom(start, point)) {
				return point;
			}
			point = translate.apply(point);
		}
		return null;
	}
}

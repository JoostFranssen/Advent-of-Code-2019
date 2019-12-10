package advent10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.Fraction;

public class Asteroid extends Point {
	private static final long serialVersionUID = 1439426092533936565L;
	private List<Asteroid> visibleAsteroids;
	
	public Asteroid(int x, int y) {
		super(x, y);
		visibleAsteroids = new ArrayList<>();
	}
	
	protected void addAsteroid(Asteroid asteroid) {
		visibleAsteroids.add(asteroid);
	}
	
	public int getVisibleAsteroidCount() {
		return visibleAsteroids.size();
	}
	
	public List<Asteroid> getVisibleAsteroids() {
		return new ArrayList<>(visibleAsteroids);
	}
	
	public boolean onLineSegment(Point origin, Point target) {
		return (new Asteroid(x - origin.x, y - origin.y)).onLineSegment(new Point(target.x - origin.x, target.y - origin.y));
	}
	
	public boolean onLineSegment(Point target) {
		if(target.x == 0) {
			if(x == 0) {
				return 0 < Math.abs(y) && Math.abs(y) < Math.abs(target.y) && Math.signum(y) == Math.signum(target.y); 
			} else {
				return false;
			}
		} else {
			Fraction targetSlope = new Fraction(target.y, target.x);
			if(x == 0) {
				return false;
			}
			Fraction slope = new Fraction(y, x);
			if(targetSlope.equals(slope)) {
				return 0 < distance(0, 0) && distance(0, 0) < target.distance(0, 0) && Math.signum(x) == Math.signum(target.x);
			} else {
				return false;
			}
		}
	}
	
	public double angleBetween(Point target) {
		return angleFromOrigin(new Point(target.x - x, target.y - y));
	}
	
	public static double angleFromOrigin(Point target) {
		return (360d - ((Math.toDegrees(Math.atan2(-target.y, target.x)) + 180d + 90d) % 360d)) % 360d;
	}
	
	@Override
	public String toString() {
		return super.toString() + ": " + visibleAsteroids.size();
	}

	public void clearAllVisibleAsteroids() {
		visibleAsteroids = new ArrayList<>();
	}
}

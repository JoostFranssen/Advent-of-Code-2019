package util;

public class MathExt {
	private MathExt() {}
	
	public static int gcd(int x, int y) {
		return (int)gcd((long)x, (long)y);
	}
	public static long gcd(long x, long y) {
		if(y == 0) {
			return x;
		} else {
			return gcd(y, x % y);
		}
	}
	
	public static int lcm(int x, int y) {
		return (int)lcm((long)x, (long)y);
	}
	public static long lcm(long x, long y) {
		return Math.abs(x * y) / gcd(x, y);
	}
}

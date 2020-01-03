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
	
	public static long primeModuloInverse(long x, long p) {
		return moduloPower(x, p - 2, p);
	}
	
	public static long moduloPower(long x, long exp, long m) {
		if(exp == 0) {
			return 1;
		}
		long result = moduloPower(x, exp / 2, m);
		result = moduloMultiplication(result, result, m);
		
		if(exp % 2 == 0) {
			return result;
		} else {
			return moduloMultiplication(x, result, m);
		}
	}
	
	public static long moduloMultiplication(long x, long y, long m) {
		long result = 0L;
		x = Math.floorMod(x, m);
		y = Math.floorMod(y, m);
		
		while(y > 0) {
			if(y % 2 == 1) {
				result = Math.addExact(result, x) % m;
			}
			x = Math.multiplyExact(2, x) % m;
			y /= 2;
		}
		
		return Math.floorMod(result, m);
	}
}

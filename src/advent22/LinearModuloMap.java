package advent22;

import util.MathExt;

public class LinearModuloMap {
	private long scale;
	private long constant;
	private long modulus;
	
	public LinearModuloMap(long scale, long constant, long modulus) {
		this.scale = scale;
		this.constant = constant;
		this.modulus = modulus;
	}
	
	public long evaluate(long argument) {
		return Math.floorMod((MathExt.moduloMultiplication(scale, argument, modulus) + constant), modulus);
	}
	
	public LinearModuloMap compose(LinearModuloMap other) { //returns other after this: other ° this
		if(modulus != other.modulus) {
			throw new IllegalArgumentException("Incompatible moduli");
		}
		
		long newScale = MathExt.moduloMultiplication(other.scale, scale, modulus);
		long newConstant = Math.floorMod(MathExt.moduloMultiplication(other.scale, constant, modulus) + other.constant, modulus);
		
		return new LinearModuloMap(newScale, newConstant, modulus);
	}
	
	public LinearModuloMap precompose(LinearModuloMap other) {
		return other.compose(this);
	}
	
	public LinearModuloMap composeSelf(long count) {
		if(count <= 1) {
			return this;
		}
		
		if(count == 2) {
			return compose(this);
		}
		
		if(count % 2 == 0) {
			return composeSelf(count / 2).composeSelf(2);
		} else {
			return compose(composeSelf(count - 1));
		}
	}
	
	public static LinearModuloMap identity(long modulus) {
		return new LinearModuloMap(1, 0, modulus);
	}
	
	public static LinearModuloMap mapFromShuffleStringInversePosition(String shuffleString, long modulus) {
		if(shuffleString.equals("deal into new stack")) {
			return new LinearModuloMap(-1L, -1L, modulus);
		}
		
		if(shuffleString.startsWith("deal with increment ")) {
			long n = Long.parseLong(shuffleString.substring("deal with increment ".length()));
			return new LinearModuloMap(MathExt.primeModuloInverse(n, modulus), 0, modulus);
		}
		
		if(shuffleString.startsWith("cut ")) {
			long n = Long.parseLong(shuffleString.substring(4));
			return new LinearModuloMap(1, n, modulus);
		}
		
		throw new IllegalArgumentException("Invalid shuffle string");
	}
	
	@Override
	public String toString() {
		return scale + " x + " + constant + " mod " + modulus;
	}
}

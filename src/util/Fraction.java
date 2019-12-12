package util;

public class Fraction {
	private int numerator, denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		if(denominator != 0) {
			this.denominator = denominator;
		} else {
			throw new IllegalArgumentException("Denominator of a Fraction cannot be 0.");
		}
		simplify();
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	public double toDecimalNotation() {
		return (double)numerator / (double)denominator;
	}
	
	public String toString() {
		return numerator + "/" + denominator;
	}
	
	public Fraction reciprocal() {
		return new Fraction(denominator, numerator);
	}
	
	public void simplify() {
		int gcd = MathExt.gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
		
		if(denominator < 0) {
			denominator *= -1;
			numerator *= -1;
		}
	}
	
	public Fraction add(int number) {
		return new Fraction(numerator + number * denominator, denominator);
	}
	public Fraction add(Fraction fraction) {
		int newDenominator = MathExt.lcm(denominator, fraction.denominator);
		int numeratorFirst = numerator * (newDenominator / denominator);
		int numeratorSecond = fraction.numerator * (newDenominator / fraction.denominator);
		return new Fraction(numeratorFirst + numeratorSecond, newDenominator);
	}
	
	public Fraction subtract(int number) {
		return add(-number);
	}
	public Fraction subtract(Fraction fraction) {
		return add(new Fraction(-fraction.numerator, fraction.denominator));
	}
	
	public Fraction multiply(int number) {
		//we do this instead of just (numerator * number) / denominator in order that for instance (Integer.MAX_VALUE / 2) * 2 return Integer.MAX_VALUE / 1 instead of -1/1.
		int gcd = MathExt.gcd(number, denominator);
		return new Fraction(numerator * (number / gcd), denominator / gcd);
	}
	public Fraction multiply(Fraction fraction) {
		int gcdFirstDenominatorSecondNumerator = MathExt.gcd(denominator, fraction.numerator);
		int gcdFirstNumeratorSecondDenominator = MathExt.gcd(numerator, fraction.denominator);
		int newNumerator = (numerator / gcdFirstNumeratorSecondDenominator) * (fraction.numerator / gcdFirstDenominatorSecondNumerator);
		int newDenominator = (denominator / gcdFirstDenominatorSecondNumerator) * (fraction.denominator / gcdFirstNumeratorSecondDenominator);
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction divide(int number) {
		int gcd = MathExt.gcd(number, numerator);
		return new Fraction(numerator / gcd, denominator * (number / gcd));
	}
	public Fraction divide(Fraction fraction) {
		return multiply(fraction.reciprocal());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + denominator;
		result = prime * result + numerator;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Fraction other = (Fraction)obj;
		if(denominator != other.denominator) {
			return false;
		}
		if(numerator != other.numerator) {
			return false;
		}
		return true;
	}
}

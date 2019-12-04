package advent04;

import java.util.Arrays;
import java.util.function.Predicate;

public class Password {
	public static final int LENGTH = 6;
	
	private int[] code = new int[LENGTH];
	private Predicate<Password> validity;
	
	public Password(int code) {
		this(code, Password::isValid);
	}
	public Password(int code, Predicate<Password> validity) {
		this.validity = validity;
		
		for(int i = LENGTH - 1; i >= 0; i--) {
			this.code[i] = code % 10;
			code /= 10;
		}
		
		if(!this.validity.test(this)) {
			next();
		}
	}
	
	public void next() {
		do {
			int carry = 0;
			code[LENGTH - 1] += 1;
			for(int i = LENGTH - 1; i > 0; i--) {
				code[i] += carry;
				
				carry = 0;
				if(code[i] > 9) {
					carry = 1;
				}
			}
			code[0] += carry;
			
			for(int i = 1; i < LENGTH; i++) {
				code[i] = Math.max(code[i - 1], code[i] % 10);
			}
		} while(!validity.test(this));
	}
	
	public boolean isNondecreasing() {
		for(int i = 0; i < LENGTH - 1; i++) {
			if(code[i] - code[i + 1] > 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean containsPair() {
		for(int i = 0; i < LENGTH - 1; i++) {
			if(code[i] == code[i + 1]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValid() {
		return containsPair() && isNondecreasing();
	}
	
	public boolean containsExactPair() {
		for(int i = 0; i < LENGTH - 1; i++) {
			if(code[i] == code[i + 1]) {
				if((i > 0 ? code[i - 1] != code[i] : true) && (i < LENGTH - 2 ? code[i + 2] != code[i] : true)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isValidStrict() {
		return containsExactPair() && isNondecreasing();
	}
	
	public int getInt() {
		int value = 0;
		for(int i = 0; i < LENGTH; i++) {
			value *= 10;
			value += code[i];
		}
		return value;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(code);
	}
}

package advent04;

import java.util.Arrays;

public class Password {
	public static final int LENGTH = 6;
	
	private int[] code = new int[LENGTH];
	
	public Password(int code) {
		for(int i = LENGTH - 1; i >= 0; i--) {
			this.code[i] = code % 10;
			code /= 10;
		}
		
		if(!isValid()) {
			next();
		}
	}
	
	public int[] next() {
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
		} while(!isValid());
		
		return code;
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

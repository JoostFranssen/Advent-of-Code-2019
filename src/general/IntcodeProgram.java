package general;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class IntcodeProgram {
	private static final int ADDITION_CODE = 1;
	private static final int MULTIPLICATION_CODE = 2;
	private static final int HALT_CODE = 99;
	
	List<Integer> values;
	
	public IntcodeProgram(List<Integer> input) {
		values = new ArrayList<>(input);
	}
	
	public List<Integer> execute() throws IllegalArgumentException {
		int i = 0;
		while(i < values.size()) {
			int value = values.get(i);
			
			if(value == HALT_CODE) {
				break;
			}
			
			IntBinaryOperator operator = getOperatorFromCode(value);
			int operandIndexLeft = values.get(i + 1);
			int operandIndexRight = values.get(i + 2);
			int resultIndex = values.get(i + 3);
			values.set(resultIndex, operator.applyAsInt(values.get(operandIndexLeft), values.get(operandIndexRight)));
			i += 4;
		}
		
		return new ArrayList<>(values);
	}
	
	private IntBinaryOperator getOperatorFromCode(int code) throws IllegalArgumentException {
		switch(code) {
			case ADDITION_CODE: return (x, y) -> x + y;
			case MULTIPLICATION_CODE: return (x, y) -> x * y;
			default: throw new IllegalArgumentException("Code not recognized as operator");
		}
	}
}

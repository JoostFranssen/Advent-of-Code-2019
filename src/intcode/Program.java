package intcode;

import java.util.ArrayList;
import java.util.List;

public class Program {
	private static final int ADDITION_CODE = 1;
	private static final int MULTIPLICATION_CODE = 2;
	private static final int INPUT_CODE = 3;
	private static final int OUTPUT_CODE = 4;
	private static final int HALT_CODE = 99;
	
	private static final int POSITION_MODE = 0;
	private static final int IMMEDIATE_MODE = 1;
	
	List<Integer> sourceCode;
	private Integer input, output;
	private int parameterMode;
	
	public Program(List<Integer> sourceCode) {
		this(sourceCode, null);
	}
	public Program(List<Integer> sourceCode, Integer input) {
		this.sourceCode = new ArrayList<>(sourceCode);
		this.input = input;
	}
	
	public List<Integer> execute() throws IllegalArgumentException {
		RUNNING:
		{
			int parameterModeDeterminator = 0;
			Operation operator = null;
			int i = 0;
			while(i < sourceCode.size()) {
				int code = sourceCode.get(i);
				
				if(operator == null) {
					if(code % 100 == HALT_CODE) {
						break RUNNING;
					}
					
					parameterModeDeterminator = sourceCode.get(i) / 100;
					
					operator = getOperatorFromCode(code % 100);
					i++;
					continue;
				}
				
				parameterMode = parameterModeDeterminator % 10;
				parameterModeDeterminator /= 10;
				
				int value = getValueFromCode(code);
				operator.addParameter(value);
				
				if(operator.checkProperty(OperationProperty.INPUT)) {
					operator.supplyInput(input);
				}
				
				if(operator.isReadyToExecute()) {
					Operation lastOperator = operator;
					operator = null;
					
					int result = lastOperator.execute();
					if(lastOperator.checkProperty(OperationProperty.STORE)) {
						sourceCode.set(code, result);
					}
					if(lastOperator.checkProperty(OperationProperty.OUTPUT)) {
						output = lastOperator.getOutput();
					}
					if(lastOperator.checkProperty(OperationProperty.JUMP)) {
						i = result;
						continue;
					}
				}
				i++;
			}
			throw new IllegalArgumentException("Program ended without halt code 99");
		}
		
		return new ArrayList<>(sourceCode);
	}
	
	private int getValueFromCode(int code) {
		switch(parameterMode) {
			case POSITION_MODE: return sourceCode.get(code);
			case IMMEDIATE_MODE: return code;
			default: throw new IllegalStateException("Parameter mode " + parameterMode + " is invalid");
		}
	}
	
	public Integer getOutput() {
		return output;
	}
	
	private Operation getOperatorFromCode(int code) throws IllegalArgumentException {
		switch(code) {
			case ADDITION_CODE: return new BinaryOperation((x, y) -> x + y, OperationProperty.STORE);
			case MULTIPLICATION_CODE: return new BinaryOperation((x, y) -> x * y, OperationProperty.STORE);
			case INPUT_CODE: return new NullaryOperation(OperationProperty.STORE, OperationProperty.INPUT);
			case OUTPUT_CODE: return new NullaryOperation(OperationProperty.OUTPUT);
			default: throw new IllegalArgumentException("Code " + code + " not recognized as operator");
		}
	}
}

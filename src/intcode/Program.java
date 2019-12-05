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
			for(int i = 0; i < sourceCode.size(); i++) {
				int code = sourceCode.get(i);
				
				if(operator == null) {
					if(code % 100 == HALT_CODE) {
						break RUNNING;
					}
					
					parameterModeDeterminator = sourceCode.get(i) / 100;
					
					operator = getOperatorFromCode(code % 100);
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
					int result = operator.execute();
					if(operator.checkProperty(OperationProperty.STORE)) {
						sourceCode.set(code, result);
					}
					if(operator.checkProperty(OperationProperty.OUTPUT)) {
						output = operator.getOutput();
					}
					operator = null;
				}
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
			case ADDITION_CODE: return new BinaryStoreOperation((x, y) -> x + y);
			case MULTIPLICATION_CODE: return new BinaryStoreOperation((x, y) -> x * y);
			case INPUT_CODE: return new NullaryOperation(OperationProperty.STORE, OperationProperty.INPUT);
			case OUTPUT_CODE: return new NullaryOperation(OperationProperty.OUTPUT);
			default: throw new IllegalArgumentException("Code " + code + " not recognized as operator");
		}
	}
}

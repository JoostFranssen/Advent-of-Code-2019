package intcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Program {
	private static final int ADDITION_CODE = 1;
	private static final int MULTIPLICATION_CODE = 2;
	private static final int INPUT_CODE = 3;
	private static final int OUTPUT_CODE = 4;
	private static final int JUMP_IF_TRUE_CODE = 5;
	private static final int JUMP_IF_FALSE_CODE = 6;
	private static final int LESS_THAN_CODE = 7;
	private static final int EQUALS_CODE = 8;
	private static final int HALT_CODE = 99;
	
	private static final int POSITION_MODE = 0;
	private static final int IMMEDIATE_MODE = 1;
	
	private List<Integer> sourceCode;
	private Queue<Integer> input;
	private Queue<Integer> output;
	private Integer lastRetrievedOutput;
	private int parameterMode;
	
	private ProgramStatus status = ProgramStatus.NOT_STARTED;
	private int pointerPosition = 0;
	private Operation currentOperation;
	private int parameterModeDeterminator = 0;
	
	public Program(List<Integer> sourceCode, Integer... input) {
		this.sourceCode = new ArrayList<>(sourceCode);
		this.input = new ArrayDeque<>(Arrays.asList(input));
		output = new ArrayDeque<>();
	}
	
	public ProgramStatus run() {
		if(status == ProgramStatus.FINISHED) {
			throw new IllegalStateException("Fininished program cannot be run");
		}
		if(status == ProgramStatus.WAITING_FOR_INPUT && input.isEmpty()) {
			return status;
		}
		if(status == ProgramStatus.RUNNING) {
			return status;
		}
		
		status = ProgramStatus.RUNNING;
		while(status == ProgramStatus.RUNNING) {
			executeIteration();
		}
		return status;
	}
	
	private void executeIteration() {
		int code;
		try {
			code = sourceCode.get(pointerPosition);
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalStateException("Invalid position in program reached: " + pointerPosition);
		}
		
		if(currentOperation == null) {
			if(code % 100 == HALT_CODE) {
				status = ProgramStatus.FINISHED;
				return;
			}
			
			parameterModeDeterminator = sourceCode.get(pointerPosition) / 100;
			
			currentOperation = getOperationFromCode(code % 100);
			pointerPosition++;
			return;
		}
		
		if(currentOperation.checkProperty(OperationProperty.INPUT)) {
			if(input.isEmpty()) {
				status = ProgramStatus.WAITING_FOR_INPUT;
				return;
			} else {
				currentOperation.supplyInput(input.remove());
				status = ProgramStatus.RUNNING;
			}
		}
		
		parameterMode = parameterModeDeterminator % 10;
		parameterModeDeterminator /= 10;
		
		int value = getValueFromCode(code);
		currentOperation.addParameter(value, code);
		
		if(currentOperation.isReadyToExecute()) {
			Operation lastOperation = currentOperation;
			currentOperation = null;
			
			int result = lastOperation.execute();
			
			if(lastOperation.checkProperty(OperationProperty.STORE)) {
				sourceCode.set(lastOperation.getStorePosition(), result);
			}
			if(lastOperation.checkProperty(OperationProperty.OUTPUT)) {
				output.add(lastOperation.getOutput());
			}
			if(lastOperation.checkProperty(OperationProperty.JUMP)) {
				if(result != -1) {
					pointerPosition = result;
					return;
				}
			}
		}
		
		pointerPosition++;
	}
	
	private int getValueFromCode(int code) {
		switch(parameterMode) {
			case POSITION_MODE: return sourceCode.get(code);
			case IMMEDIATE_MODE: return code;
			default: throw new IllegalStateException("Parameter mode " + parameterMode + " is invalid");
		}
		
		
	}
	
	public void supplyInput(Integer input) {
		this.input.add(input);
	}
	
	public Integer peekNextInput() {
		return input.peek();
	}
	
	public boolean hasOutput() {
		return !output.isEmpty();
	}
	
	public Integer getNextOutput() {
		return lastRetrievedOutput = output.remove();
	}
	
	public Integer getLastRetrievedOutput() {
		return lastRetrievedOutput;
	}
	
	public ProgramStatus getStatus() {
		return status;
	}
	
	public List<Integer> getSourceCode() {
		return new ArrayList<>(sourceCode);
	}
	
	private Operation getOperationFromCode(int code) throws IllegalArgumentException {
		switch(code) {
			case ADDITION_CODE:
				return new BinaryOperation((x, y) -> x + y, OperationProperty.STORE);
			case MULTIPLICATION_CODE:
				return new BinaryOperation((x, y) -> x * y, OperationProperty.STORE);
			case INPUT_CODE:
				return new NullaryOperation(OperationProperty.STORE, OperationProperty.INPUT);
			case OUTPUT_CODE:
				return new UnaryOperation(x -> x, OperationProperty.OUTPUT);
			case JUMP_IF_TRUE_CODE:
				return new BinaryOperation((x, y) -> x != 0 ? y : -1, OperationProperty.JUMP);
			case JUMP_IF_FALSE_CODE:
				return new BinaryOperation((x, y) -> x == 0 ? y : -1, OperationProperty.JUMP);
			case LESS_THAN_CODE:
				return new BinaryOperation((x, y) -> x < y ? 1 : 0, OperationProperty.STORE);
			case EQUALS_CODE:
				return new BinaryOperation((x, y) -> x == y ? 1 : 0, OperationProperty.STORE);
			default:
				throw new IllegalArgumentException("Code " + code + " not recognized as operator");
		}
	}
}

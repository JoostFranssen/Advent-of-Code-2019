package intcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private static final int ADJUST_RELATIVE_BASE_CODE = 9;
	private static final int HALT_CODE = 99;
	
	private static final int POSITION_MODE = 0;
	private static final int IMMEDIATE_MODE = 1;
	private static final int RELATIVE_MODE = 2;
	
	private Map<Long, Long> sourceCode;
	private Queue<Long> input;
	private Queue<Long> output;
	private Long lastRetrievedOutput;
	private int parameterMode;
	private long relativeBase;
	
	private ProgramStatus status = ProgramStatus.NOT_STARTED;
	private Long pointerPosition = 0L;
	private Operation currentOperation;
	private long parameterModeDeterminator = 0L;
	
	public Program(List<Long> sourceCode, Long... input) {
		this.sourceCode = new HashMap<>();
		for(int i = 0; i < sourceCode.size(); i++) {
			this.sourceCode.put((long)i, sourceCode.get(i));
		}
		
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
		long code;
		try {
			code = getFromSourceCode(pointerPosition);
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalStateException("Invalid position in program reached: " + pointerPosition);
		}
		
		if(currentOperation == null) {
			if(code % 100 == HALT_CODE) {
				status = ProgramStatus.FINISHED;
				return;
			}
			
			parameterModeDeterminator = getFromSourceCode(pointerPosition) / 100;
			
			currentOperation = getOperationFromCode((int)(code % 100));
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
		
		parameterMode = (int)(parameterModeDeterminator % 10);
		parameterModeDeterminator /= 10;
		
		long value = getValueFromCode(code);
		currentOperation.addParameter(value, code + (parameterMode == RELATIVE_MODE ? relativeBase : 0));
		
		if(currentOperation.isReadyToExecute()) {
			Operation lastOperation = currentOperation;
			currentOperation = null;
			
			long result = lastOperation.execute();
			
			if(lastOperation.checkProperty(OperationProperty.STORE)) {
				sourceCode.put(lastOperation.getStorePosition(), result);
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
	
	private long getValueFromCode(long code) {
		switch(parameterMode) {
			case POSITION_MODE: return getFromSourceCode(code);
			case IMMEDIATE_MODE: return code;
			case RELATIVE_MODE: return getFromSourceCode(code + relativeBase);
			default: throw new IllegalStateException("Parameter mode " + parameterMode + " is invalid");
		}
	}
	
	private long getFromSourceCode(Long position) {
		if(position < 0) {
			throw new IllegalArgumentException("Cannot access negative positions of the program");
		}
		if(sourceCode.containsKey(position)) {
			return sourceCode.get(position);
		} else {
			return 0L;
		}
	}
	
	private long adjustRelativeBase(long value) {
		relativeBase += value;
		return relativeBase;
	}
	
	public void supplyInput(Long input) {
		this.input.add(input);
	}
	
	public Long peekNextInput() {
		return input.peek();
	}
	
	public boolean hasOutput() {
		return !output.isEmpty();
	}
	
	public Long getNextOutput() {
		return lastRetrievedOutput = output.remove();
	}
	
	public Long getLastRetrievedOutput() {
		return lastRetrievedOutput;
	}
	
	public ProgramStatus getStatus() {
		return status;
	}
	
	public Map<Long, Long> getSourceCode() {
		return new HashMap<>(sourceCode);
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
			case ADJUST_RELATIVE_BASE_CODE:
				return new UnaryOperation(x -> adjustRelativeBase(x));
			default:
				throw new IllegalArgumentException("Code " + code + " not recognized as operator");
		}
	}
}

package intcode;

import java.util.function.IntBinaryOperator;

public class BinaryOperation extends Operation {
	
	private IntBinaryOperator function;
	
	public BinaryOperation(IntBinaryOperator function, OperationProperty... operationProperties) {
		super(operationProperties);
		this.function = function;
	}
	
	@Override
	public int execute() {
		return function.applyAsInt(getParameter(0), getParameter(1));
	}

	@Override
	protected int getNecessaryParameters() {
		return 3;
	}
}

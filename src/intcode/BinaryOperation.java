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
		int result = function.applyAsInt(getParameter(0), getParameter(1));
		if(checkProperty(OperationProperty.OUTPUT)) {
			setOutput(result);
		}
		return result;
	}

	@Override
	protected int getNecessaryParameters() {
		return checkProperty(OperationProperty.STORE) ? 3 : 2;
	}
}

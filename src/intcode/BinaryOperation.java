package intcode;

import java.util.function.LongBinaryOperator;

public class BinaryOperation extends Operation {
	
	private LongBinaryOperator function;
	
	public BinaryOperation(LongBinaryOperator function, OperationProperty... operationProperties) {
		super(operationProperties);
		this.function = function;
	}
	
	@Override
	public long execute() {
		long result = function.applyAsLong(getParameter(0), getParameter(1));
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

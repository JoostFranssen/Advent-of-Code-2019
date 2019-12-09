package intcode;

import java.util.function.LongUnaryOperator;

public class UnaryOperation extends Operation {
	private LongUnaryOperator function;
	
	public UnaryOperation(LongUnaryOperator function, OperationProperty... operationProperties) {
		super(operationProperties);
		this.function = function;
	}

	@Override
	public long execute() {
		long result = function.applyAsLong(getParameter(0));
		if(checkProperty(OperationProperty.OUTPUT)) {
			setOutput(result);
		}
		return result;
	}
	
	@Override
	protected int getNecessaryParameters() {
		return checkProperty(OperationProperty.STORE) ? 2 : 1;
	}
}

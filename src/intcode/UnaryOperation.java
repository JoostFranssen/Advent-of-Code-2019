package intcode;

import java.util.function.IntUnaryOperator;

public class UnaryOperation extends Operation {
	private IntUnaryOperator function;
	
	public UnaryOperation(IntUnaryOperator function, OperationProperty... operationProperties) {
		super(operationProperties);
		this.function = function;
	}

	@Override
	public int execute() {
		int result = function.applyAsInt(getParameter(0));
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

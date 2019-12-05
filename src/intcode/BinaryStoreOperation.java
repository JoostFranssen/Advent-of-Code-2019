package intcode;

import java.util.function.IntBinaryOperator;

public class BinaryStoreOperation extends Operation {
	
	private IntBinaryOperator function;
	
	public BinaryStoreOperation(IntBinaryOperator function) {
		super();
		this.function = function;
		addOperationProperty(OperationProperty.STORE);
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

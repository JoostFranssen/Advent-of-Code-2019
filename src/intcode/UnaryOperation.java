package intcode;

import java.util.function.IntUnaryOperator;

public class UnaryOperation extends Operation {
	private IntUnaryOperator function;
	
	public UnaryOperation(IntUnaryOperator function) {
		super();
		this.function = function;
	}

	@Override
	protected int getNecessaryParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}
}

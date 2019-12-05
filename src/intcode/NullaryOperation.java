package intcode;

public class NullaryOperation extends Operation {
	
	public NullaryOperation(OperationProperty... operationProperties) {
		super(operationProperties);
	}
	
	@Override
	public int execute() {
		int result = -1;
		if(checkProperty(OperationProperty.INPUT)) {
			result = getInput();
		}
		if(checkProperty(OperationProperty.OUTPUT)) {
			setOutput(getParameter(0));
		}
		return result;
	}

	@Override
	protected int getNecessaryParameters() {
		return 1;
	}
	
}

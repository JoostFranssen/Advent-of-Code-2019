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
		return result;
	}

	@Override
	protected int getNecessaryParameters() {
		return checkProperty(OperationProperty.STORE) ? 1 : 0;
	}
	
}

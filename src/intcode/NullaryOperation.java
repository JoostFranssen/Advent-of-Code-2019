package intcode;

public class NullaryOperation extends Operation {
	
	public NullaryOperation(OperationProperty... operationProperties) {
		super(operationProperties);
	}
	
	@Override
	public long execute() {
		long result = -1L;
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

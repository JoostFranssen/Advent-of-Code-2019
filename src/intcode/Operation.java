package intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Operation {
	private int necessaryParameterCount;
	private List<Integer> parameters;
	private Set<OperationProperty> operationProperties;
	private int input, output;
	
	public Operation() {
		parameters = new ArrayList<Integer>();
		operationProperties = new HashSet<OperationProperty>();
		necessaryParameterCount = getNecessaryParameters();
	}
	public Operation(OperationProperty... operationProperties) {
		this();
		this.operationProperties.addAll(Arrays.asList(operationProperties));
	}
	
	protected abstract int getNecessaryParameters();
	
	public final void addParameter(Integer parameter) {
		parameters.add(parameter);
	}
	
	protected final void addOperationProperty(OperationProperty property) {
		operationProperties.add(property);
	}
	
	protected final boolean checkProperty(OperationProperty property) {
		return operationProperties.contains(property);
	}
	
	public final boolean isReadyToExecute() {
		return necessaryParameterCount == parameters.size();
	}
	
	public final void supplyInput(Integer input) {
		this.input = input;
	}
	
	public final int getInput() {
		return input;
	}
	
	protected final void setOutput(int output) {
		this.output = output;
	}
	
	public final int getOutput() {
		return output;
	}
	
	public int getStorePosition() throws IncorrectOperationPropertyException {
		if(!checkProperty(OperationProperty.STORE)) {
			throw new IncorrectOperationPropertyException("Not a storing operation");
		}
		
		return getParameter(necessaryParameterCount - 1);
	}
	
	public abstract int execute();
	
	protected final int getParameterCount() {
		return parameters.size();
	}
	
	protected final int getParameter(int index) {
		return parameters.get(index);
	}
	
	public final List<Integer> getParameters() {
		return new ArrayList<>(parameters);
	}
}

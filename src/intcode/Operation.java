package intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Operation {
	private List<Long> parameters;
	private List<Long> parametersCode; //always contains the code provided and not the actual value
	private Set<OperationProperty> operationProperties;
	private long input, output;
	
	public Operation() {
		parameters = new ArrayList<>();
		parametersCode = new ArrayList<>();
		operationProperties = new HashSet<OperationProperty>();
	}
	public Operation(OperationProperty... operationProperties) {
		this();
		this.operationProperties.addAll(Arrays.asList(operationProperties));
	}
	
	protected abstract int getNecessaryParameters();
	
	public final void addParameter(Long parameter, Long code) {
		parameters.add(parameter);
		parametersCode.add(code);
	}
	
	protected final void addOperationProperty(OperationProperty property) {
		operationProperties.add(property);
	}
	
	protected final void removeOperationPRoperty(OperationProperty property) {
		operationProperties.remove(property);
	}
	
	protected final boolean checkProperty(OperationProperty property) {
		return operationProperties.contains(property);
	}
	
	public final boolean isReadyToExecute() {
		return getNecessaryParameters() == parameters.size();
	}
	
	public final void supplyInput(Long input) {
		this.input = input;
	}
	
	public final long getInput() {
		return input;
	}
	
	protected final void setOutput(long output) {
		this.output = output;
	}
	
	public final long getOutput() {
		return output;
	}
	
	public long getStorePosition() {
		if(checkProperty(OperationProperty.STORE)) {
			return parametersCode.get(getNecessaryParameters() - 1);
		} else {
			throw new IllegalStateException("Operation does not store");
		}
	}
	
	public abstract long execute();
	
	protected final int getParameterCount() {
		return parameters.size();
	}
	
	protected final long getParameter(int index) {
		return parameters.get(index);
	}
	
	public final List<Long> getParameters() {
		return new ArrayList<>(parameters);
	}
}

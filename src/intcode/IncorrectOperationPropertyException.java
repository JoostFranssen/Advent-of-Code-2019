package intcode;

public class IncorrectOperationPropertyException extends Exception {
	private static final long serialVersionUID = -7209790357129860226L;

	public IncorrectOperationPropertyException() {
		super();
	}
	
	public IncorrectOperationPropertyException(String message) {
		super(message);
	}
	
	public IncorrectOperationPropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IncorrectOperationPropertyException(Throwable cause) {
		super(cause);
	}
	
	public IncorrectOperationPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

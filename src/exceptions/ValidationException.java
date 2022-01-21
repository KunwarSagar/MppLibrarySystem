package exceptions;

@SuppressWarnings("serial")
final public class ValidationException extends Exception {
	public ValidationException() {
		super();
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(Throwable t) {
		super(t);
	}
}

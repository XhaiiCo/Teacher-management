package be.helha.aemt.groupeA1.exception;

public class InvalidEmailException extends Exception{

	private String message   ;

	public InvalidEmailException(String message) {
		this.message = message ;
	}

	public InvalidEmailException() {
		this("L'email doit avoir ce format: example@helha.be")	 ;
	}

	@Override
	public String getMessage() {
		return this.message ;
	}
}

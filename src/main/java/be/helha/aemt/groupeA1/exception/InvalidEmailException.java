package be.helha.aemt.groupeA1.exception;

public class InvalidEmailException extends EmailException{

	public InvalidEmailException() {
		super("L'email doit avoir ce format: example@helha.be")	 ;
	}

	public InvalidEmailException(String message) {
		super(message) ;
	}

}

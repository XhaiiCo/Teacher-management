package be.helha.aemt.groupeA1.exception;

public class EmailException extends Exception{

	private String message   ;

	public EmailException(String message) {
		this.message = message ;
	}

	public EmailException() {
		this("Il y a une erreur avec l'email")	 ;
	}

	@Override
	public String getMessage() {
		return this.message ;
	}

}

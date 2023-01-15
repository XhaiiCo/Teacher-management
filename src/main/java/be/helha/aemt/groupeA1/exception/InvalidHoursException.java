package be.helha.aemt.groupeA1.exception;

public class InvalidHoursException extends Exception{

	
	private String message;

	public InvalidHoursException(String message) {
		this.message = message;
	}
	
	public InvalidHoursException()
	{
		this ("L'heure ne peut pas dépasser 1400 heures");
	}

	public String getMessage() {
		return message;
	}
	
	
	
	
}

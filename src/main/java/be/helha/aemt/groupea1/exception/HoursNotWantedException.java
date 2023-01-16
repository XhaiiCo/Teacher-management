package be.helha.aemt.groupea1.exception;

public class HoursNotWantedException extends Exception{

	public HoursNotWantedException() {
		super("L'heures n'est pas voulu");
	}
	
	public HoursNotWantedException(String message) {
		super(message) ;
	}
}

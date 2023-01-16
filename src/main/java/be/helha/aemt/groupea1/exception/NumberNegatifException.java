package be.helha.aemt.groupea1.exception;

public class NumberNegatifException extends Exception{

	
	public NumberNegatifException() {
		super("Le chiffre ne peut pas être négatif");
	}
	
	public NumberNegatifException(String message) {
		super(message) ;
	}
}

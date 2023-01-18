package be.helha.aemt.groupea1.exception;

public class OutOfBoundNbAssignement extends Exception{

	private String message   ;

	public OutOfBoundNbAssignement(String message) {
		this.message = message ;
	}

	public OutOfBoundNbAssignement() {
		this("Le nombre d'attribution ne peut pas être plus grand que le nombtre de groupe")	 ;
	}

	@Override
	public String getMessage() {
		return this.message ;
	}

}

package be.helha.aemt.groupea1.exception;

public class AllHoursAssignmedException extends Exception{

	private String message   ;

	public AllHoursAssignmedException(String message) {
		this.message = message ;
	}

	public AllHoursAssignmedException() {
		this("Toutes les heures on été attribuées pour ce groupe")	 ;
	}

	@Override
	public String getMessage() {
		return this.message ;
	}

}

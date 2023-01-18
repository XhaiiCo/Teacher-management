package be.helha.aemt.groupea1.entities;

public enum EAssignationStatus {

	Done("Terminé"),
	InProgress("En cours"),
	Nothing("Pas attribué") ;
	
	private String text ;
	private EAssignationStatus(String text) {
		this.text = text ;
	}
	
	public String getText(){
		return this.text ;
	}
	
}

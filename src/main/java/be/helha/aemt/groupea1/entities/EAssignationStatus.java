package be.helha.aemt.groupea1.entities;

public enum EAssignationStatus {

	Done("Terminée"),
	InProgress("En cours"),
	Nothing("Pas attribuée") ;
	
	private String text ;
	private EAssignationStatus(String text) {
		this.text = text ;
	}
	
	public String getText(){
		return this.text ;
	}
	
}

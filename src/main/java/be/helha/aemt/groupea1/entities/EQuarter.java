package be.helha.aemt.groupea1.entities;

public enum EQuarter {
	
	Q1("Q1"),
	Q2("Q2") ;
	
	private String text ;
	
	EQuarter(String text) {
		this.text = text ;
	}

	String getText(){
		return this.text ;
	}

}

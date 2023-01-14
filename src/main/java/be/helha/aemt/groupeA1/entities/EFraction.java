package be.helha.aemt.groupeA1.entities;

public enum EFraction {

	f480(480),
	f750(750) ;
	
	private int fraction ;

	 EFraction(int fraction) {
		this.fraction = fraction ;
	}
	 
	 int getFraction(){
		 return this.fraction ;
	 }
}

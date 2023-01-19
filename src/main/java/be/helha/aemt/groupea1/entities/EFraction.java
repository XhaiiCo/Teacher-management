package be.helha.aemt.groupea1.entities;

public enum EFraction {

	f480(480),
	f750(750);
	
	private int fraction;
	
	EFraction(int fraction) {
		this.fraction = fraction;
	}
	 
	public int getFraction(){
		return this.fraction;
	}
	
	public static EFraction findByNumber(int intValue) {
		
		for(EFraction fraction : EFraction.values()) {
			
			if(fraction.getFraction() == intValue)
			{
				return fraction;
			}
		}
		return null;
	}
}

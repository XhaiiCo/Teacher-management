package be.helha.aemt.groupeA1.entities;

public enum ERole {

	S("S"),
	DDE("DDE"),
	DDOM("DDOM");
	
	private String role;

	private ERole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}

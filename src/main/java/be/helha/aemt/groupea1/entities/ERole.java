package be.helha.aemt.groupea1.entities;

public enum ERole {

	S("Secrétaire"),
	DDE("Directeur de département"),
	DDOM("Directeur de domaine");

	private String text;

	private ERole(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}

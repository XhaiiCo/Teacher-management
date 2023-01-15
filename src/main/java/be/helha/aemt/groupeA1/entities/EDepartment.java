package be.helha.aemt.groupeA1.entities;

public enum EDepartment {
	D1("D1"),
	D2("D2"),
	D3("D3"),
	D4("D4"),
	D5("D5"),
	D6("D6");
	
	private String department;

	private EDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}
	
}

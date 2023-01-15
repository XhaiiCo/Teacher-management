package be.helha.aemt.groupeA1.control;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class NavigationControl implements Serializable {
	
	public String example() {
		return "test.xhtml?faces-redirect=true" ;//For redirect to the page, useful for auth
	}
	
	public String doNext()
	{
		return "S/teacherCRUD.xhtml";
	}
}
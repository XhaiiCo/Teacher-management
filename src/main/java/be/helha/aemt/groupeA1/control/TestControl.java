package be.helha.aemt.groupeA1.control;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class TestControl implements Serializable {

	
	private String email;
	private String role;
	
	public TestControl() 
	{
		 HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 
		 email = request.getUserPrincipal().getName();
		 
		 if(request.isUserInRole("S"))
		 {
	         role = "Secrétaire";
	     }
		 else if(request.isUserInRole("DDE"))
	     {
	         role = "Directeur de département";
	     }
		 else if(request.isUserInRole("DDOM"))
	     {
			 role = "Directeur de domaine";
	     }
		 else
		 {
			 role = "Pas de role";
		 }
	}
	
	public String getEmail() {
        return email;
    }

	public String getRole() {
		return role;
	}
	
	
}

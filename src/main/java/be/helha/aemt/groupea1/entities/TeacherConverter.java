package be.helha.aemt.groupea1.entities;

import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value="teacherConverter")
public class TeacherConverter implements Converter<Object> {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		String[] splittedString = value.split("\\+");
		try {
			Teacher t = new Teacher(splittedString[0], splittedString[1], splittedString[2], splittedString[3]);
			return t;
		} catch (InvalidEmailException e) {
			e.printStackTrace();
		}
		return "error (email invalide)";
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value instanceof Teacher){
			Teacher element = (Teacher) value;
			return element.getLastName() +"+"+ element.getFirstName() +"+"+ element.getEmail() +"+"+ element.getNote();
		}
		return null;
	}

}
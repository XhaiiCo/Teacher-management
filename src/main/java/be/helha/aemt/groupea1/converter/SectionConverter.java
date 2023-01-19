package be.helha.aemt.groupea1.converter;

import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value="sectionConverter")
public class SectionConverter implements Converter<Object> {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		String[] splittedString = value.split("\\+");
		Section s = new Section(new Department(splittedString[0]), splittedString[1]);
		return s;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value instanceof Section){
			Section element = (Section) value;
			return element.getDepartment().getName() +"+"+ element.getName();
		}
		return null;
	}
	
}

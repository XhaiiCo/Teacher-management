package be.helha.aemt.groupea1.converter;

import be.helha.aemt.groupea1.entities.Department;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value="departmentConverter")
public class DepartmentConverter implements Converter<Object> {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Department d = new Department(value);
		return d;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value instanceof Department){
			Department element = (Department) value;
			return element.getName();
		}
		return null;
	}
	
}

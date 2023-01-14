package be.helha.aemt.groupeA1.control;

import java.io.Serializable;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class FillDatabaseControl implements Serializable{

	@EJB
	private TeacherEJB teacherEJB ;

	public void doAddTestTeacher() {

		for(int i = 1 ; i <= 20 ; i++) {
			Teacher t = new Teacher("N" + i, "P" + i, "n" + i + "p@helha.be", null) ;

			teacherEJB.add(t) ;
		}

	}
}

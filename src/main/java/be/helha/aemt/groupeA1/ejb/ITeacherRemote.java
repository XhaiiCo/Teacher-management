package be.helha.aemt.groupeA1.ejb;

import be.helha.aemt.groupeA1.entities.Teacher;
import jakarta.ejb.Remote;

@Remote
public interface ITeacherRemote {
	
	public Teacher add(Teacher enseignant);
}

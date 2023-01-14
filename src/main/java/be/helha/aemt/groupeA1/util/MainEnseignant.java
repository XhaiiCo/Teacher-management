package be.helha.aemt.groupeA1.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import be.helha.aemt.groupeA1.ejb.TeacherEJB;
import be.helha.aemt.groupeA1.entities.Teacher;


public class MainEnseignant {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try 
		{

			Context ctx = new InitialContext();
			
			TeacherEJB bean = (TeacherEJB) ctx.lookup("java:global/groupeA1/TeacherEJB!be.helha.aemt.groupeA1.ejb.ITeacherRemote");
			
			Teacher e = new Teacher("Altares", "Valentin", "altaresv@helha.be", "note");
			
			
			bean.add(e);
			
		} 
		catch (NamingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
	}

}

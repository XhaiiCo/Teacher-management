package be.helha.aemt.groupea1.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.ejb.SectionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.ejb.UtilisateurEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.EFraction;
import be.helha.aemt.groupea1.entities.ERole;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.entities.Utilisateur;
import be.helha.aemt.groupea1.exception.HoursNotWantedException;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.InvalidHoursException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class FillDatabaseControl implements Serializable{

	@EJB
	private TeacherEJB teacherEJB ;

	@EJB 
	private UtilisateurEJB utilisateurEJB;

	@EJB 
	private DepartmentEJB departmentEJB;

	@EJB
	private MissionEJB missionEJB;

	@EJB
	private SectionEJB sectionEJB;

	@EJB
	private AAEJB aaEJB;

	@EJB
	private UEEJB ueEJB;

	//Ok
	public void doAddTestTeacher() {

		for(int i = 1 ; i <= 20 ; i++) {
			try {
				Teacher t = new Teacher("N" + i, "P" + i, "n" + i + "p@helha.be", null) ;
				teacherEJB.add(t) ;
			}catch (InvalidEmailException e) {
				System.err.println(e.getMessage()) ;
			}
		}
	}

	public void doAddDatas() throws NumberNegatifException, HoursNotWantedException {

		//Departments
		List<Department> departments = new ArrayList<>() ;
		for(int i = 1 ; i <= 6 ; i++)
		{
			Department d = new Department("Departement" + i);
			for(int j = 1 ; j <= 3 ; j++) {
				Section s = new Section(d, "D"+i +"-Section"+j) ;
				for(int k = 1 ; k <= 3 ; k++) {
					UE ue = new UE("2022-2023", "1Bi", "Code"+ i+j+k, "UE"+i+j+k, 6) ;
					for(int l = 1 ; l <= 2 ; l++) {
						AA aa = new AA("2022-2023", "Code"+i+j+k+l, "AA"+i+j+k+l, 3, 40, 20, 20, 1, 20, EFraction.f480, ue, s, d) ; 
						ue.addAA(aa) ;
					}
					s.addUe(ue);
				}
				d.addSection(s);
			}

			departments.add(departmentEJB.add(d)) ;
		}
	}

	//OK
	public void doAddMockUtilisateur() {

		for(int i = 1 ; i <= 6 ; i++)
		{
			Utilisateur dde;
			Utilisateur secr;

			Department department = new Department("Departement" + i);

			try 
			{
				dde = new Utilisateur("NDirDept" + i , "PDirDept" + i, "ndirdept" + i + "p@helha.be", 
						"helha", ERole.DDE, new Section(department, "Section" + i));

				secr = new Utilisateur("SDept" + i , "PDept" + i, "sdept" + i + "p@helha.be", 
						"helha", ERole.S, new Section(department, "Section" + i));

				utilisateurEJB.add(dde);
				utilisateurEJB.add(secr);
			} 
			catch (InvalidEmailException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Utilisateur ddom;
			try 
			{
				ddom = new Utilisateur("NDirDom", "PDirDom", "ndirdomp@helha.be", 
						"helha", ERole.DDOM, new Section(department, "Section" + i));

				utilisateurEJB.add(ddom);
			} 
			catch (InvalidEmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void doAddTestMission() {

		for(int i=1 ; i <= 10 ; i++)
		{
			try {	
				Mission m=new Mission("2022-2023", "t" + i , i * 100);

				missionEJB.add(m);

			}catch (InvalidHoursException e) {
				e.printStackTrace();
			}

		}
	}
}

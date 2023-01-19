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
import be.helha.aemt.groupea1.entities.EAssignationStatus;
import be.helha.aemt.groupea1.entities.EFraction;
import be.helha.aemt.groupea1.entities.ERole;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.MissionDepartment;
import be.helha.aemt.groupea1.entities.MissionSection;
import be.helha.aemt.groupea1.entities.MissionTransversale;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.entities.Utilisateur;
import be.helha.aemt.groupea1.exception.HoursNotWantedException;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.InvalidHoursException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
import be.helha.aemt.groupea1.util.Toast;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/*
 insert ignore into UTILISATEUR('EMAIL', 'NOM', 'PASSWORD', 'PRENOM', 'ROLE')
 values
 ('secr', 'nSecr', 'd+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=', 'pSecr', 'S'),
 ('dept', 'nDept', 'd+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=', 'pDept', 'DDE'),
 ('dom', 'nDom', 'd+Rn6wFp6C538JDfIXoyM1fGoVepjAN15vbbr+ApyDo=', 'pDom', 'DDOM');
 
 */
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

	public void doAddAll() throws NumberNegatifException, HoursNotWantedException {
		this.doAddDatas();
		this.doAddMockUtilisateur();
		this.doAddTestMission();
		this.doAddTestTeacher();
	}

	//Ok
	public void doAddTestTeacher() {

		for(int i = 1 ; i <= 20 ; i++) {
			try {
				Teacher t = new Teacher("N" + i, "P" + i, "n" + i + "p@helha.be", "") ;
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
					UE ue = new UE("2022-2023", "1Bi", "Code"+ i+j+k, "UE"+i+j+k, 6, s) ;
					for(int l = 1 ; l <= 2 ; l++) {
						AA aa = new AA( "Code"+i+j+k+l, "AA"+i+j+k+l, 3, 20, 20, 3, 2, 20, EFraction.f480, ue ) ; 
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
		Utilisateur dde;
		Utilisateur secr;
		Utilisateur ddom;

		try 
		{
			dde = new Utilisateur("Ndept", "Pdept", "dept@helha.be", 
					"helha", ERole.DDE);

			secr = new Utilisateur("Nsecr", "Psecr", "secr@helha.be", 
					"helha", ERole.S);

			ddom = new Utilisateur("Ndom", "Pdom", "dom@helha.be", 
					"helha", ERole.DDOM);

			utilisateurEJB.add(dde);
			utilisateurEJB.add(secr);
			utilisateurEJB.add(ddom);
		} 
		catch (InvalidEmailException e) 
		{
			e.printStackTrace();
		}
	}

	public void doAddTestMission() {

		for(int i=1 ; i <= 3 ; i++)
		{
			Department d = new Department("Departement" + i);
			Section section = new Section(d, "Section" + i);
			List<Teacher> teachers = new ArrayList<Teacher>();

			try {
				Mission mt = new MissionTransversale("2022-2023", "mt" + i , i * 100, teachers);
				missionEJB.add(mt);

				Mission md = new MissionDepartment("2022-2023", "md" + i , i * 100, teachers, d);
				missionEJB.add(md);

				Mission ms = new MissionSection("2022-2023", "ms" + i , i * 100, teachers, section);
				missionEJB.add(ms);

			}catch (InvalidHoursException e) {
				Toast.showErrorToast("Erreur", e.getMessage());
			}

		}

	}
}

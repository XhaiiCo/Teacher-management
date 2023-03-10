package be.helha.aemt.groupea1.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Assignment;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Mission;
import be.helha.aemt.groupea1.entities.MissionDepartment;
import be.helha.aemt.groupea1.entities.MissionSection;
import be.helha.aemt.groupea1.entities.MissionTransversale;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class MissionDAO extends AbstractDAO<Mission>{

	@EJB
	private DepartmentDAO departmentDAO;

	@EJB
	private SectionDAO sectionDAO;

	@EJB
	private TeacherDAO teacherDAO;

	public MissionDAO() {
		super(Mission.class);
	}

	/** find the mission on his entitled
	 * 
	 * @return the mission or null if not found
	 *
	 */
	public Mission find(Mission mission) {
		if (mission==null) return null;
		
		String rq = "SELECT m From Mission m where m.entitled=?1 and m.academicYear=?2";

		TypedQuery<Mission>query = em.createQuery(rq,Mission.class);
		query.setParameter(1,mission.getEntitled());
		query.setParameter(2,mission.getAcademicYear());

		List<Mission> result=query.getResultList();

		if (mission instanceof MissionDepartment) {
			List<Mission> tmp = new ArrayList<Mission>();
			result.forEach(e -> {
				if (e instanceof MissionDepartment)
					if(((MissionDepartment) e).getDepartment().equals(((MissionDepartment)mission).getDepartment()))
						tmp.add(((MissionDepartment) e));
			});
			if (tmp.isEmpty()) return null;
			return tmp.get(0);
		} else if (mission instanceof MissionSection) {
			List<Mission> tmp = new ArrayList<Mission>();
			result.forEach(e -> {
				if (e instanceof MissionSection)
					if(((MissionSection) e).getSection().equals(((MissionSection)mission).getSection()))
						tmp.add(((MissionSection) e));
			});
			if (tmp.isEmpty()) return null;
			return tmp.get(0);
		}

		if (result.isEmpty()) return null;
		return result.get(0);
	}

	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on entitle and academicYear
	 */
	@Override
	public Mission add(Mission mission) {
		if(mission == null) return null ;

		List<Teacher> teachers = new ArrayList<Teacher>();
		List<Teacher> ts = mission.getTeachers();

		Teacher teacher;
		for (int i=0; i<ts.size(); i++) {
			teacher = teacherDAO.find(ts.get(i));
			if(teacher != null)
				teachers.add(teacher);
		}
		mission.setTeachers(teachers);

		if (mission instanceof MissionDepartment) {
			MissionDepartment missionDep = (MissionDepartment) mission;
			Department department = departmentDAO.find(missionDep.getDepartment());

			if(department != null)
				missionDep.setDepartment(department);

			if(find(missionDep) != null) return null ;
			return super.add(missionDep);

		}
		else if (mission instanceof MissionSection) {
			MissionSection missionSec = (MissionSection) mission;

			Department department = departmentDAO.find(missionSec.getSection().getDepartment());

			if(department != null)
				missionSec.getSection().setDepartment(department);

			Section section = sectionDAO.find(missionSec.getSection());

			if(section != null)
				missionSec.setSection(section);

			if(find(missionSec) != null) return null ;
			return super.add(missionSec);
		}
		else if (mission instanceof MissionTransversale) {
			MissionTransversale missionTrans = (MissionTransversale) mission;
			if(find(missionTrans) != null) return null ;
			return super.add(missionTrans);
		}

		return null;

	}

	/**
	 * Redefinition of the method to add a condition to check that there are no duplicates on the entitle and the academicYear
	 */
	public Mission update(Mission mission) {

		//Find the old Mission
		Mission oldMission = findById(mission.getId()) ;
		if(mission == null) return null;

		List<Teacher> teachers = new ArrayList<Teacher>();
		List<Teacher> ts = mission.getTeachers();

		Teacher teacher;
		for (int i=0; i<ts.size(); i++) {
			teacher = teacherDAO.find(ts.get(i));
			if(teacher != null)
				teachers.add(teacher);
		}
		mission.setTeachers(teachers);

		if(oldMission.getEntitled().equals(mission.getEntitled()) && oldMission.getAcademicYear().equals(mission.getAcademicYear()))
			return super.update(mission);

		if(find(mission) == null)
			return super.update(mission);

		return null ;
	}

	@Override
	public Mission delete(Mission mission) {
		if(mission == null) return mission;
		
		mission.setTeachers(null);

		Mission toRemove = em.merge(mission);

		em.remove(toRemove);

		return mission;
	}

	public List<Mission> findByTeacher(Teacher teacher){
		if(teacher == null) return null ;

		List<Mission> missions = super.findAll() ;
		List<Mission> result = new ArrayList<>() ;

		missions.forEach(mission -> {
			if(mission.getTeachers().contains(teacher)) 
				result.add(mission) ;
		});

		return result ;
	}
}

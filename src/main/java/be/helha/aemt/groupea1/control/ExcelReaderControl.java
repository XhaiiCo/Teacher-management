package be.helha.aemt.groupea1.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.ejb.MissionEJB;
import be.helha.aemt.groupea1.ejb.SectionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.EFraction;
import be.helha.aemt.groupea1.entities.MissionDepartment;
import be.helha.aemt.groupea1.entities.MissionSection;
import be.helha.aemt.groupea1.entities.MissionTransversale;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.InvalidHoursException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named
@SessionScoped
public class ExcelReaderControl implements Serializable {

	//Item received from the multipart/form-data in the xhtml
    private Part file;
    
    //Variable that will contain the imported Excel file
    private Workbook workbook;
    
    @EJB
    private DepartmentEJB departmentEJB;
    
    @EJB
    private SectionEJB sectionEJB;
    
    @EJB
    private TeacherEJB teacherEJB;
    
    @EJB
    private UEEJB ueEJB; 
    
    @EJB
    private AAEJB aaEJB;
    
    @EJB
    private MissionEJB missionEJB;
    
    private List<Department> departments = new ArrayList<>() ;
    
    /*Function to upload the excel file that will be save in the workbook variable
    and then the different import function will convert datas in each page in entities*/ 
    public void doImportDatasFromExcel() {
    	this.uploadFile();
    	
    	this.importDepartments();
    	this.importSections();
    	this.importTeachers();
    	this.importUEs();
    	this.importAAs();
    	this.importMissions();
    	this.saveDepartment(); 
    }
    
    public void uploadFile() 
    {
        try 
        {
            InputStream input = file.getInputStream();
            this.workbook = new HSSFWorkbook(input);     
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public Part getFile() {
        return file;
    }
    
    public void setFile(Part file) {
        this.file = file;
    }
    
    /*Function to check if a cell is empty, we do this because if we try
    getStringCellValue() on an empty cell it will make an error*/
    public String checkBlankCell(Row row, int index) {
    	Cell cell = row.getCell(index);
    	if(cell == null) 
    	{
    	    return null;
    	} 
    	else 
    	{
    	    return cell.getStringCellValue();
    	}
    }
    
    public void importDepartments() {
    	
    	/*try to retrieve the sheet named "Départements" from the downloaded 
    	Excel file, if there is no file of that name, it will return null and 
    	we will have to deal with it. Otherwise we will get an error*/
    	Sheet sheet = workbook.getSheet("Départements");
    	
    	if(sheet == null) return;
    	
    	//loop on all rows excluding the row 0 so we don't get headers
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		/*get the value of the cell using the index, by doing 
    		this we must have the column in the Excel sheet in the 
    		same order as the indexes defined in the program.*/ 
    		String departmentName = row.getCell(0).getStringCellValue();
    		
    		//departmentEJB.add(new Department(departmentName));
    		this.departments.add(new Department(departmentName)) ;
		}
    }
    
    public void saveDepartment() {
    	this.departments.forEach(department -> {
    		departmentEJB.add(department) ;
    	});
    }

    public Department findOrAddDepartment(Department department) {
    	int ind = this.departments.indexOf(department) ;
    	if(ind != -1) {
    		return this.departments.get(ind) ;
    	}
    	this.departments.add(department) ;

    	return department ;
    }
    
    public void importSections() {
    	Sheet sheet = workbook.getSheet("Sections");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		String sectionName = row.getCell(1).getStringCellValue();
    		
    		Department department = this.findOrAddDepartment(new Department(departmentName)) ;

    		department.addSection(new Section(department, sectionName));
		}
    }
    
    public void importTeachers() {
    	Sheet sheet = workbook.getSheet("Enseignants");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String teacherEmail = row.getCell(0).getStringCellValue();
    		String teacherLastName = row.getCell(1).getStringCellValue();
    		String teacherFirstName = row.getCell(2).getStringCellValue();
    		String teacherNote = this.checkBlankCell(row, 3);
    		
    		try 
    		{
				teacherEJB.add(new Teacher(teacherLastName, teacherFirstName, teacherEmail, teacherNote));
			} 
    		catch (InvalidEmailException e) 
    		{
				e.printStackTrace();
			}
		}
    }
    
    public Section findOrAddSection(Department department, Section section) {
    	int ind = department.getSections().indexOf(section) ;
    	if(ind != -1)
    		return department.getSections().get(ind) ;
    	
    	department.addSection(section) ;
    	return section ; 
    }
    
    public void importUEs() {
    	Sheet sheet = workbook.getSheet("UEs");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String yearRangeUE = row.getCell(0).getStringCellValue();
    		String blocUE = row.getCell(1).getStringCellValue();
    		String codeUE = row.getCell(2).getStringCellValue();
    		int creditsUE = (int) row.getCell(3).getNumericCellValue();
    		String entitledUE = row.getCell(4).getStringCellValue();
    		
    		String departmentName = row.getCell(5).getStringCellValue();
    		String sectionName = row.getCell(6).getStringCellValue();
    		
    		Department department = this.findOrAddDepartment(new Department(departmentName)) ;
    		Section section = this.findOrAddSection(department, new Section(department, sectionName)) ;
    		
    		try 
    		{
				section.addUe(new UE(yearRangeUE, blocUE, codeUE, entitledUE, creditsUE, section));
			} 
    		catch (NumberNegatifException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
    }
    
    public UE findOrAddUE(Section section, UE ue) {
    	int ind = section.getUes().indexOf(ue) ;
    	if(ind != -1)
    		return section.getUes().get(ind) ;
    	
    	section.addUe(ue) ;
    	return ue ; 
    }

    public void importAAs() {
    	Sheet sheet = workbook.getSheet("AAs");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String codeAA = row.getCell(0).getStringCellValue();
    		int creditsAA = (int) row.getCell(1).getNumericCellValue();
    		String entitledAA = row.getCell(2).getStringCellValue();
    		int fractionAA = (int) row.getCell(3).getNumericCellValue();
    		int hoursQ1AA = (int) row.getCell(4).getNumericCellValue();
    		int hoursQ2AA = (int) row.getCell(5).getNumericCellValue();
    		int nbStudentAA = (int) row.getCell(6).getNumericCellValue();
    		int nbGroup1AA = (int) row.getCell(7).getNumericCellValue();
    		int nbGroup2AA = (int) row.getCell(8).getNumericCellValue();
    		String departmentName = row.getCell(9).getStringCellValue();
    		String sectionName = row.getCell(10).getStringCellValue();
    		String yearRangeUE = row.getCell(11).getStringCellValue();
    		String codeUE = row.getCell(12).getStringCellValue();
    		
    		Department department = this.findOrAddDepartment( new Department(departmentName)) ;
    		Section section = this.findOrAddSection(department, new Section(department, sectionName)) ;
    		UE ue = this.findOrAddUE(section, new UE(codeUE, yearRangeUE, section)) ;
    		
    		try 
    		{
				ue.addAA(new AA(codeAA, entitledAA, creditsAA, hoursQ1AA, hoursQ2AA, nbGroup1AA, nbGroup2AA, nbStudentAA, 
						EFraction.findByNumber(fractionAA), ue));
			} 
    		catch (NumberNegatifException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public void importMissions() {
    	Sheet sheet = workbook.getSheet("Missions");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String typeMission = row.getCell(0).getStringCellValue();
    		String yearRangeMission = row.getCell(1).getStringCellValue();
    		String entitledMission = row.getCell(2).getStringCellValue();
    		int hoursMission = (int) row.getCell(3).getNumericCellValue();
    		String departmentName = this.checkBlankCell(row, 4);
    		String sectionName = this.checkBlankCell(row, 5);
    		
    		try 
    		{
	    		if(typeMission.equals("MissionDepartment"))
	    		{
	    			Department department = new Department(departmentName);
	    			
					missionEJB.add(new MissionDepartment(yearRangeMission, entitledMission, hoursMission, department));
	    		}
	    		else if(typeMission.equals("MissionSection")) 
	    		{
	    			Department department = new Department(departmentName);
	    			Section section = new Section(department, sectionName);
	    			    			
	    			missionEJB.add(new MissionSection(yearRangeMission, entitledMission, hoursMission, section));
	    		}
	    		else if(typeMission.equals("MissionTransversale")) 
	    		{
	    			missionEJB.add(new MissionTransversale(yearRangeMission, entitledMission, hoursMission));
	    		}
			} 
    		catch (InvalidHoursException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
}

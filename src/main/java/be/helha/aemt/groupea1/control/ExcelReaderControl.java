package be.helha.aemt.groupea1.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import be.helha.aemt.groupea1.ejb.AAEJB;
import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.ejb.SectionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.entities.AA;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.EFraction;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.exception.HoursNotWantedException;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
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
    
    /*Function to upload the excel file that will be save in the workbook variable
    and then the different import function will convert datas in each page in entities*/ 
    public void doImportDatasFromExcel() {
    	this.uploadFile();
    	
    	this.importDepartments();
    	this.importSections();
    	this.importTeachers();
    	this.importUEs();
    	this.importAAs();
    }
    
    public void uploadFile() 
    {
        try 
        {
            InputStream input = file.getInputStream();
            this.workbook = new XSSFWorkbook(input);     
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
    	    return "";
    	} 
    	else 
    	{
    	    return cell.getStringCellValue();
    	}
    }
    
    /* Get the sheet based on it's name so we don't need to use index and so have
    the sheet don't have to be in the same place in the workbook, otherwise
    we use index to get the row cell. After that we get the value in the cell
    and we can create an instance of Department with this information*/
    public void importDepartments() {
    	Sheet sheet = workbook.getSheet("Départements");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		
    		departmentEJB.add(new Department(departmentName));
		}
    }
    
    public void importSections() {
    	Sheet sheet = workbook.getSheet("Sections");
    	
    	if(sheet == null) return;
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		String sectionName = row.getCell(1).getStringCellValue();
    		
    		Department sectionDepartment = new Department(departmentName);
    		sectionEJB.add(new Section(sectionDepartment, sectionName));
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
    		
    		Department sectionDepartment = new Department(departmentName);
    		
    		try 
    		{
				ueEJB.add(new UE(yearRangeUE, blocUE, codeUE, entitledUE, creditsUE, new Section(sectionDepartment, sectionName)));
			} 
    		catch (NumberNegatifException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
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
    		int nbGroupAA = (int) row.getCell(6).getNumericCellValue();
    		int nbStudentAA = (int) row.getCell(7).getNumericCellValue();
    		String departmentName = row.getCell(8).getStringCellValue();
    		String sectionName = row.getCell(9).getStringCellValue();
    		String yearRangeUE = row.getCell(10).getStringCellValue();
    		String codeUE = row.getCell(11).getStringCellValue();
    		
    		Department sectionDepartment = new Department(departmentName);
    		Section section = new Section(sectionDepartment, sectionName);
    		UE ue = new UE(codeUE, yearRangeUE, section);
    		
    		try 
    		{
				aaEJB.add(new AA(codeAA, entitledAA, creditsAA, hoursQ1AA, hoursQ2AA, nbGroupAA, nbStudentAA, 
						EFraction.findByNumber(fractionAA), ue));
			} 
    		catch (NumberNegatifException | HoursNotWantedException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
		}
    }    
}

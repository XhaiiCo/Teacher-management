package be.helha.aemt.groupea1.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import be.helha.aemt.groupea1.ejb.DepartmentEJB;
import be.helha.aemt.groupea1.ejb.SectionEJB;
import be.helha.aemt.groupea1.ejb.TeacherEJB;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named
@SessionScoped
public class ExcelReaderControl implements Serializable {

    private Part file;
    private Workbook workbook;
    
    @EJB
    private DepartmentEJB departmentEJB;
    
    @EJB
    private SectionEJB sectionEJB;
    
    @EJB
    private TeacherEJB teacherEJB;
    
    public void doImportDatasFromExcel() {
    	this.uploadFile();
    	
    	this.importDepartment();
    	this.importSection();
    	this.importTeacher();
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
    
    public void importDepartment() {
    	
    	//gérer ça car si feuille existe pas renvoie null
    	Sheet sheet = workbook.getSheet("Départements");
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		
    		departmentEJB.add(new Department(departmentName));
		}
    }
    
    public void importSection() {
    	//gérer ça car si feuille existe pas renvoie null
    	Sheet sheet = workbook.getSheet("Sections");
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		String sectionName = row.getCell(1).getStringCellValue();
    		
    		Department sectionDepartment = new Department(departmentName);
    		sectionEJB.add(new Section(sectionDepartment, sectionName));
		}
    }
    
    public void importTeacher() {
    	//gérer ça car si feuille existe pas renvoie null
    	Sheet sheet = workbook.getSheet("Enseignants");
    	
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
}

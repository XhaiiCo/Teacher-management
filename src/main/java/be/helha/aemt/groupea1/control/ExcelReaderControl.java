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
import be.helha.aemt.groupea1.ejb.UEEJB;
import be.helha.aemt.groupea1.entities.Department;
import be.helha.aemt.groupea1.entities.Section;
import be.helha.aemt.groupea1.entities.Teacher;
import be.helha.aemt.groupea1.entities.UE;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.NumberNegatifException;
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
    
    @EJB
    private UEEJB ueEJB; 
    
    /*Function to upload the excel file that will be save in the workbook variable
    and then the different import function will convert datas in each page in entities*/ 
    public void doImportDatasFromExcel() {
    	this.uploadFile();
    	
    	this.importDepartments();
    	this.importSections();
    	this.importTeachers();
    	this.importUEs();
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
    	
    	//gérer ça car si feuille existe pas renvoie null
    	Sheet sheet = workbook.getSheet("Départements");
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String departmentName = row.getCell(0).getStringCellValue();
    		
    		departmentEJB.add(new Department(departmentName));
		}
    }
    
    public void importSections() {
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
    
    public void importTeachers() {
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
    
    public void importUEs() {
    	//gérer ça car si feuille existe pas renvoie null
    	Sheet sheet = workbook.getSheet("UEs");
    	
    	for(int i = 1; i <= sheet.getLastRowNum(); i++) 
		{
    		Row row = sheet.getRow(i);
    		
    		String yearRangeUE = row.getCell(0).getStringCellValue();
    		String blocUE = row.getCell(1).getStringCellValue();
    		String codeUE = row.getCell(2).getStringCellValue();
    		int creditsUE = (int) row.getCell(3).getNumericCellValue();
    		String entitledUE = row.getCell(4).getStringCellValue();
    		
    		try 
    		{
				ueEJB.add(new UE(yearRangeUE, blocUE, codeUE, entitledUE, creditsUE));
			} 
    		catch (NumberNegatifException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}

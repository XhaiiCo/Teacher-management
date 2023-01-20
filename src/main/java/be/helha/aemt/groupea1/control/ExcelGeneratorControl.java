package be.helha.aemt.groupea1.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import jakarta.servlet.http.HttpServlet;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import java.util.Scanner;

@Named
@SessionScoped
public class ExcelGeneratorControl extends HttpServlet implements Serializable {
	
	//call the doGet here, so we can get the current request and response and give it to it
	public void generateExcelTemplate() throws ServletException, IOException 
	{
		 this.doGet((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(), 
				 (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //Create a new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        //create cell font
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        /*Access the data.txt that is in src folder, in this file there the name
        of the sheet and titles of column*/
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("data.txt");
             
        /*Read data from data.txt and create sheets and column names, 
        we change of page when the scanner read a dot*/
        Scanner scanner = new Scanner(stream, "UTF-8");
        while (scanner.hasNextLine()) 
        {
            String sheetName = scanner.nextLine();
            Sheet sheet = workbook.createSheet(sheetName);
            
            Row headerRow = sheet.createRow(0);
            int columnNum = 0;
            while (scanner.hasNextLine()) 
            {
            	String header = scanner.nextLine();
            	if(header.equals(".")) break;
            	
            	Cell cell = headerRow.createCell(columnNum);
            	cell.setCellValue(header);
            	cell.setCellStyle(headerCellStyle);
            	
            	sheet.autoSizeColumn(columnNum);
            	columnNum++;
            }
        }
        scanner.close();
        
        //Set the response headers and define the name of the file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=template.xls");
        
        //Write the workbook to the output stream
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

}

package be.helha.aemt.groupea1.control;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServlet;
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

public class ExcelGeneratorControl extends HttpServlet  {
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Create a new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        //create cell font
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        // Read the text file
        File file = new File(("C:/data.txt"));
        Scanner scanner = new Scanner(file, "UTF-8");
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
        
        // Set the response headers
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=template.xls");
        
        // Write the workbook to the output stream
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

}

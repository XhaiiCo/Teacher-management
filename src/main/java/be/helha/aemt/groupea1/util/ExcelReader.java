package be.helha.aemt.groupea1.util;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader 
{	
	public Workbook workbook;
	
	public void chooseExcelFile() throws IOException 
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		
		FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
		workbook = new XSSFWorkbook(fis);
	}
	
	public Sheet fetchSheet(int index) {
		return this.workbook.getSheetAt(index);
	}

}

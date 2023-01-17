package be.helha.aemt.groupea1.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader 
{
	private Workbook workbook;
	
	public void chooseExcelFile() throws IOException 
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		
		FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
		this.workbook = new XSSFWorkbook(fis);
	}
	
	/*
	public Sheet getExcelSheet() throws IOException {
		
		FileInputStream fis = new FileInputStream(this.chooseFile());
		Workbook workbook = new XSSFWorkbook(fis);
		return workbook.getSheetAt(0);
	}*/
	
	
	
	
	
}

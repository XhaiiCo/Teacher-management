package be.helha.aemt.groupea1.util;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			System.out.println(PasswordHash.hashPassword("helha"));
		} catch (PasswordHashingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*
		try 
		{			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File file = fileChooser.getSelectedFile();

			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) 
			{
			    Row row = sheet.getRow(i);
			    
			    String departmentName = row.getCell(0).getStringCellValue();
			    
			    String sectionName = row.getCell(2).getStringCellValue();
			    

			    
			    Department department = new Department(departmentName);
			    
			    Section section = new Section(department, sectionName);
			    
			    System.out.println(department);
			    System.out.println(section);
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
		
	}
}

package be.helha.aemt.groupea1.util;

public class Main {

	public static void main(String[] args) {

/*
		public class ExcelGenerator 
		{

		    public void generateExcel(HttpServletResponse response) throws IOException 
		    {
		        Workbook workbook = new HSSFWorkbook();
		        Sheet sheet = workbook.createSheet("Data");

		        // Entêtes de colonne
		        String[] columns = {"ID", "Nom", "Prenom", "Email"};
		        Row headerRow = sheet.createRow(0);
		        for (int i = 0; i < columns.length; i++) 
		        {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columns[i]);
		        }


		        // Définir les entêtes de colonne comme filtre automatique
		        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, columns.length - 1));

		        // Télécharger le fichier
		        response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=data.xls");
		        workbook.write(response.getOutputStream());
		        workbook.close();
		    }
		}*/
		
	}
}

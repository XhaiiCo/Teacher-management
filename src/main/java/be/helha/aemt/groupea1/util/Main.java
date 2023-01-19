package be.helha.aemt.groupea1.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		File file = new File("lib/data.txt");
        try {
			Scanner scanner = new Scanner(file, "UTF-8");
			String sheetName = scanner.nextLine();
			
			System.out.println(sheetName);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

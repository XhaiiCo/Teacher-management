package be.helha.aemt.groupea1.util;

import be.helha.aemt.groupea1.exception.PasswordHashingException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(PasswordHash.hashPassword("helha"));
		} catch (PasswordHashingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

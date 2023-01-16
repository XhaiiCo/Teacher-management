package be.helha.aemt.groupea1.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import be.helha.aemt.groupea1.exception.PasswordHashingException;

public class PasswordHash {

	
	public static String hashPassword(String password) throws PasswordHashingException
	{
		try 
		{
			//hash password with SHA-256
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hashedPassword = digest.digest(password.getBytes("UTF-8"));
	        
	        //encode hash in base64
	        String encodedPassword = Base64.getEncoder().encodeToString(hashedPassword);
	        
	        return encodedPassword;
		} 
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) 
		{
			throw new PasswordHashingException("Error hashing password", e);
		}
	}
}



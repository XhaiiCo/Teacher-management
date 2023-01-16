package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;
import be.helha.aemt.groupea1.exception.InvalidEmailException;
import be.helha.aemt.groupea1.exception.PasswordHashingException;
import be.helha.aemt.groupea1.util.PasswordHash;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Utilisateur implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	private String nom,
	prenom,
	password ;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private ERole role ;
		
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Section section;
		
	public Utilisateur() {}
	
	public Utilisateur(String nom, String prenom, String email, String password, ERole role, Section section) throws InvalidEmailException {
		this.nom = nom;
		this.prenom = prenom;
		this.setEmail(email);
		this.setPassword(password);
		this.role = role;
		this.section = section;
	}	
	
	public Utilisateur(String email) throws InvalidEmailException {
		this.setEmail(email);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidEmailException{
		
		if(isValidEmail(email)) this.email = email;

		else throw new InvalidEmailException() ;
	}
	
	public boolean isValidEmail(String email) {
	    String emailRegex = "^[a-zA-Z0-9._%+-]+@helha\\.be$";
	    Pattern pat = Pattern.compile(emailRegex);
	    if (email == null)
	        return false;
	    return pat.matcher(email).matches();
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		
		try 
		{
			this.password = PasswordHash.hashPassword(password);
		} 
		catch (PasswordHashingException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	
	@Override
	/**
	 * Equals on the email
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(email, other.email);
	}

	
}

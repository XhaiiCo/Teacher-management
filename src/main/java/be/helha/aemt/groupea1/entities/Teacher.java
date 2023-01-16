package be.helha.aemt.groupea1.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import be.helha.aemt.groupea1.exception.InvalidEmailException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Teacher implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String lastName,
	firstName,

	//Contains notes/remarks about the teacher
	note;
	
	@Column(unique = true)
	private String email;
	
	//Uncomment when the class is done
	private Assignment assignment ;

	public Teacher() {}
	
	public Teacher(String lastName, String firstName, String email, String note)  throws InvalidEmailException{
		this.lastName = lastName;
		this.firstName = firstName;
		this.setEmail(email);
		this.note = note;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}
	
	/**
	 * Equals on the id and email
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return this.id + " " +  this.lastName + " " + this.firstName + " " + this.email + " " + this.note ;
	}
}

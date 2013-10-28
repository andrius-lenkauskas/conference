package lt.nfq.conference.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class User {
	private int id;

	@Size(min = 1, max = 255)
	private String name;

	@Size(min = 1, max = 255)
	private String surname;

	@Email
	@Size(min = 1, max = 255)
	private String email;

	@Size(min = 1, max = 100)
	private String country;

	@Size(min = 1, max = 100)
	private String town;

	@Size(min = 1)
	private String password;

	private String role;

	public User() {

	}

	public User(User o) {
		id = o.id;
		name = o.name;
		surname = o.surname;
		email = o.email;
		country = o.country;
		town = o.town;
		password = o.password;
		role = o.role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
}

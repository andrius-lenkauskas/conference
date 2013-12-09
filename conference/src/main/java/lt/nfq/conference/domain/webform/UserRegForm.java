package lt.nfq.conference.domain.webform;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserRegForm {

	@Size(min = 1, max = 255)
	private String name;

	@Size(min = 1, max = 255)
	private String surname;

	@Email
	@Size(min = 1, max = 255)
	private String email;

	private int country;

	private int town;

	@NotEmpty
	private String password;

	public UserRegForm() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getTown() {
		return town;
	}

	public void setTown(int town) {
		this.town = town;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

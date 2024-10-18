package entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import enums.UserRole;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "second_name", nullable = false)
	private String secondName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	// @Column(nullable = false , name = "role")
	// @Enumerated(EnumType.STRING)
	// private UserRole role;

	public User() {	

	}

	public User(String firstName, String secondName, String email, String password) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.password = password;
		// this.role = role;

	}

	public User(Long id, String firstName, String secondName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.password = password;
		// this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecond_name(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// public UserRole getRole() {
	// 	return role;
	// }

	// public void setRole(UserRole role) {
	// 	this.role = role;
	// }

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}

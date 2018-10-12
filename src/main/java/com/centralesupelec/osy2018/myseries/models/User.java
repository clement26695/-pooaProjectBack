package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;
	
    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
	
	@Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
	private String email;
	
	@Size(max = 50)
    @Column(name = "last_name", length = 50)
	private String lastName;
	
	@Size(max = 50)
    @Column(name = "first_name", length = 50)
	private String firstName;
	
	@CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
	private ZonedDateTime dateCreation;
	
	private LocalDate birthdate;
	private String description;
	
	@OneToOne
	@JoinColumn(name = "watchlist_id")
	private Watchlist watchlist;
	
	public User() {
	}
	
	public User(long id, String login, String password, String email, String lastName, String firstName,
			ZonedDateTime dateCreation, LocalDate birthdate, String description) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateCreation = dateCreation;
		this.birthdate = birthdate;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public ZonedDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(ZonedDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

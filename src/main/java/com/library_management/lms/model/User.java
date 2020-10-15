package com.library_management.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "email_id")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "roles")
	private String roles;

	@Column(name = "book_id_1")
	private int bookId1;

	@Column(name = "book_id_2")
	private int bookId2;

	@Column(name = "book_id_3")
	private int bookId3;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public int getBookId1() {
		return bookId1;
	}

	public void setBookId1(int bookId1) {
		this.bookId1 = bookId1;
	}

	public int getBookId2() {
		return bookId2;
	}

	public void setBookId2(int bookId2) {
		this.bookId2 = bookId2;
	}

	public int getBookId3() {
		return bookId3;
	}

	public void setBookId3(int bookId3) {
		this.bookId3 = bookId3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User(int id, String name, String email, String password, String roles, int bookId1, int bookId2,
			int bookId3) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.bookId1 = bookId1;
		this.bookId2 = bookId2;
		this.bookId3 = bookId3;
	}

	public User() {
	}

}

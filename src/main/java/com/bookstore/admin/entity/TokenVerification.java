package com.bookstore.admin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bookstore.admin.utility.ServiceUtility;


@Entity
@Table(name="token_verification")
public class TokenVerification {
	
	private static final int EXPIRATION = 60 * 24;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable=false, name="user_id")
	private User user;
	
	private Date expiryDate;
	
	public TokenVerification(){}
	
	public TokenVerification(final String token, final User user) {
		this.token = token;
		this.user = user;
		this.expiryDate = ServiceUtility.calculateExpiryDate(EXPIRATION);
	}
	
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = ServiceUtility.calculateExpiryDate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "TokenVerification [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}

}

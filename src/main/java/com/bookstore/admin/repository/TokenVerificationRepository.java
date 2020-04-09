package com.bookstore.admin.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookstore.admin.entity.TokenVerification;
import com.bookstore.admin.entity.User;



public interface TokenVerificationRepository extends JpaRepository<TokenVerification, Long> {
	
	TokenVerification findByToken(String token);
	
	TokenVerification findByUser(User user);
	
	Stream<TokenVerification> findAllByExpiryDateLessThan(Date now);
	
	@Modifying
	@Query("delete from TokenVerification t where t.expiryDate <= :now")
	void deleteAllExpiredSince(@Param("now") Date now);

}

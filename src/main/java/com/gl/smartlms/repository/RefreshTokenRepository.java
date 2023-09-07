package com.gl.smartlms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.smartlms.model.RefreshToken;
import com.gl.smartlms.model.User;


//==============================================================
//= RefreshToken JPA Repository
//=================================================================
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	public Optional<RefreshToken> findByToken(String token);

	public void deleteByUserInfo(User member);



}

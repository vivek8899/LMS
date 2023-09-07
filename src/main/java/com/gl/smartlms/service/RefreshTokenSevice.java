package com.gl.smartlms.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.model.RefreshToken;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.RefreshTokenRepository;
import com.gl.smartlms.repository.UserRepository;

import jakarta.transaction.Transactional;



//==============================================================
//= RefreshTokenService  Class
//=================================================================
@Service
@Transactional
public class RefreshTokenSevice {

	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    
    

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }



	public void deleteToken(User member) {
		refreshTokenRepository.deleteByUserInfo(member);
		
	}
}

package com.rubypaper.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rubypaper.DataNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
    //유저 조회 (update)
    @Transactional
    public void update(SiteUser siteuser) {
    	SiteUser user = userRepository.findById(siteuser.getId()).orElseThrow(() -> {
    		return new IllegalArgumentException("회원찾기 실패");
    	});
    	//String rawPassword = siteuser.getPassword();
    	//String encPassword = passwordEncoder.encode(rawPassword);
    	//user.setPassword(encPassword);
    	user.setEmail(siteuser.getEmail());
    	this.userRepository.save(user);
    }
    
    public  SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
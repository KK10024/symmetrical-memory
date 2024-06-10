package com.rubypaper.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubypaper.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	@Autowired
    private final AdminRepository adminRepository;
    
    public List<SiteUser> getUsers() {
        return adminRepository.findAll();
    }

}

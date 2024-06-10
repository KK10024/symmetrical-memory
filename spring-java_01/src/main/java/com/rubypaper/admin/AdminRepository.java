package com.rubypaper.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubypaper.user.SiteUser;

public interface AdminRepository 
extends JpaRepository<SiteUser, Integer>{
}

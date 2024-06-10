package com.rubypaper.user;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
    
    @GetMapping("/user_update_form")
	public String udate_form(@AuthenticationPrincipal UserDetails userDetails , Model model) {   	
    	SiteUser siteuser = this.userService.getUser(userDetails.getUsername());
    	model.addAttribute("siteuser", siteuser);
    	return "user_update_form";
    }
    
    @PostMapping("/user_update_form")
    public String UpdateUser (@Valid SiteUser siteuser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/user_update_form";
        }
    	userService.update(siteuser);
        return "redirect:/user/user_update_form";
    }
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        
        try {
            userService.create(userCreateForm.getUsername(), 
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }
}
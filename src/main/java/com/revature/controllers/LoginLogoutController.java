package com.revature.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.LoginResponse;
import com.revature.models.User;
import com.revature.services.LoginLogoutService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
public class LoginLogoutController {
    
    @Autowired
    private LoginLogoutService loginLogoutService;

    @Autowired
    private HttpSession session;
    
    @PostMapping("/login-credentials")
    public ResponseEntity<String> loginCredentials(@RequestBody User user) {
        LoginResponse tempUser = loginLogoutService.login(user);
        
        if(tempUser.getUser() != null){
            loginLogoutService.twoFactorAuthentication(tempUser.getUser());
            return new ResponseEntity<>("Credentials Pass", HttpStatus.OK);
        }
        if(tempUser.getMessage().equals("Account Locked")) {
            return new ResponseEntity<>("Account Locked", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Failed to Login", HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/login-token")
    public ResponseEntity<User> loginToken(@RequestBody Map<String, Integer> token) {
        LoginResponse tempUser = loginLogoutService.loginToken(token.get("token"));
        if(tempUser.getUser() != null) {
            session.setAttribute("CurrentUser", tempUser.getUser().getUserId());
            session.setAttribute("CurrentUserRole", tempUser.getUser().getUserRole());
            return new ResponseEntity<>(tempUser.getUser(), HttpStatus.OK);
        }
        return new ResponseEntity<>(tempUser.getUser(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/log-out")
    public ResponseEntity<String> logout(){
        session.invalidate();
        return new ResponseEntity<>("Logged out Successfully", HttpStatus.OK);
    }
}

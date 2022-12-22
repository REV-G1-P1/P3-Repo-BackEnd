package com.revature.controllers;

import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.LoginResponse;
import com.revature.models.User;
import com.revature.services.LoginLogoutService;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:3000/")
public class LoginLogoutController {
    
    @Autowired
    private LoginLogoutService loginLogoutService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user, HttpSession session) {
        LoginResponse tempUser = loginLogoutService.login(user);
        if(tempUser.getUser() != null){
            session.setAttribute("CurrentUser", tempUser.getUser().getUserId());
            LoginResponse response = new LoginResponse(tempUser.getUser(), Base64.getEncoder().encodeToString(session.getId().getBytes()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if(tempUser.getMessage().equals("Account Locked")) {
            return new ResponseEntity<>(tempUser, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(tempUser, HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/log-out/{cookieId}")
    public ResponseEntity<String> logout(@PathVariable String cookieId, HttpSession session){
        byte[] decodedCookieIdBytes = Base64.getDecoder().decode(cookieId);
        String decodedCookieId = new String(decodedCookieIdBytes);
        loginLogoutService.removeSessionById(decodedCookieId);
        return new ResponseEntity<>("Logged out Successfully", HttpStatus.OK);
    }
}

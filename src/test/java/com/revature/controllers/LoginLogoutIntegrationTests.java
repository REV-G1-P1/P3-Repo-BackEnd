package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AccountInformation;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.p3bankbackend.P3BankBackendApplication;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = P3BankBackendApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class LoginLogoutIntegrationTests {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserController userController;
	
	
	@BeforeEach
	public void resetDatabase() throws Exception {
		System.out.println("Run before each test");
		userRepo.deleteAll();
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(123456789L); //Change this to valid number to test
		user.setLoginToken(777);
		userController.createUser(user);
		
	}
	
	@Test
	@Transactional
	public void testSuccessfulLoginCredentials() throws Exception {
		
		User tempUser = new User();
		tempUser.setEmail("test@email.com");
		tempUser.setPassword("testPassword");
		
		String context = objectMapper.writeValueAsString(tempUser);
		
		mockMvc.perform(post("/login/login-credentials")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andExpect(content().string("Credentials Pass"));
		
		
	}
	
	@Test
	@Transactional
	public void testSuccessfulLogout() throws Exception {
		
		mockMvc.perform(get("/login/log-out"))
				.andExpect(status().isOk())
				.andExpect(content().string("Logged out Successfully"));
		
		
	}

}

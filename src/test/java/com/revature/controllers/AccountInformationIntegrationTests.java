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
public class AccountInformationIntegrationTests {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private MockHttpSession mockSession;
	
	@Autowired
    private HttpSession session;
	
	
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
		user.setPhoneNumber(1234567890L);
		user.setLoginToken(777);
		userController.createUser(user);
		User tempUser = userService.findUserByEmail(user.getEmail());
		
		//Log In user
		Map<String, Integer> token = Map.of("token", 777);
		String context = objectMapper.writeValueAsString(token);
		MvcResult result = mockMvc.perform(post("/login/login-token")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andReturn();
		
		mockSession = (MockHttpSession)result.getRequest().getSession();
		session.setAttribute("CurrentUser", tempUser.getUserId());
        session.setAttribute("CurrentUserRole", tempUser.getUserRole());
	}
	
	@Test
	@Transactional
	public void testSuccessfulAccountBalanceUpdate() throws Exception {
		
		User tempUser = userService.findUserByEmail("test@email.com");
		List<AccountInformation> userAccounts = tempUser.getAccountInformation();
		AccountInformation account = userAccounts.get(0);
		Map<String, String> newAccountBalance = new HashMap<String, String>();
		newAccountBalance.put("accountNumber", account.getAccountNumber().toString());
		newAccountBalance.put("accountNumber", account.getBalance().toString());
		String context = objectMapper.writeValueAsString(account);
		Map<String, Object> sessionAttrs = new HashMap<String, Object>();
		sessionAttrs.put("CurrentUser", tempUser.getUserId());
		sessionAttrs.put("CurrentRole", tempUser.getUserRole());
		
		mockMvc.perform(put("/accounts//update/balance").sessionAttrs(sessionAttrs)
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andExpect(content().string("Account balance updated successfully"));
		
		
	}

}

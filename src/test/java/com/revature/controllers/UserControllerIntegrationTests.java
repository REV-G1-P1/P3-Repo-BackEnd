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
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.p3bankbackend.P3BankBackendApplication;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = P3BankBackendApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserControllerIntegrationTests {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;
	
	
	@BeforeEach
	public void resetDatabase() {
		System.out.println("Run before each test");
		userRepo.deleteAll();
	}
	
	
	@Test
	@Transactional
	public void testSuccessfulRegistration() throws Exception {
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		
		String context = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created successfully"));
		
		Optional<User> registered = userRepo.findUserByEmail(user.getEmail());
		if(registered.isPresent()) {
			assertNotNull(registered.get());
			assertEquals("testFirstName", registered.get().getFirstName());
			assertEquals("testLastName", registered.get().getLastName());
			assertEquals("test@email.com", registered.get().getEmail());
			assertEquals(123456789, registered.get().getSSN());
			assertEquals(1234567890, registered.get().getPhoneNumber());
		}
		
	}
	
	@Test
	@Transactional
	public void testUnsuccessfulRegistration() throws Exception {
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		userService.createUser(user);
		String context = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isConflict())
				.andExpect(content().string("User already exists"));
	}
	
	@Test
	@Transactional
	public void testSuccessfulValidation() throws Exception {
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		String context = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post("/users/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andExpect(content().string("User is available"));
	}
	
	@Test
	@Transactional
	public void testSuccessfulUpdate() throws Exception {
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		userService.createUser(user);
		
		Address address = new Address();
		address.setCity("testCity");
		address.setState("TS");
		address.setStreetAddress("testStreet1");
		address.setStreetAddressLine2("testStreet2");
		address.setZipCode(12345);
		user.setAddress(address);
		
		String context = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(put("/users/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andExpect(content().string("User updated successfully"));
		
		Optional<User> registered = userRepo.findUserByEmail(user.getEmail());
		if(registered.isPresent()) {
			assertNotNull(registered.get());
			assertEquals("testCity", registered.get().getAddress().getCity());
			assertEquals("TS", registered.get().getAddress().getState());
			assertEquals("testStreet1", registered.get().getAddress().getStreetAddress());
			assertEquals("testStreet2", registered.get().getAddress().getStreetAddressLine2());
			assertEquals(12345, registered.get().getAddress().getZipCode());
		}
		
		
	}
	
	@Test
	@Transactional
	public void testSuccessfulFindById() throws Exception {
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		userService.createUser(user);
		User testUser = userService.findUserByEmail(user.getEmail());
		Integer testUserId = testUser.getUserId();
		
		mockMvc.perform(get("/users/get/{id}", testUserId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("testFirstName"))
				.andExpect(jsonPath("$.lastName").value("testLastName"))
				.andExpect(jsonPath("$.email").value("test@email.com"))
				.andExpect(jsonPath("$.ssn").value(123456789))
				.andExpect(jsonPath("$.phoneNumber").value(1234567890));
		
	}
	
	@Test
	@Transactional
	public void testSuccessfulFindCurrentUser() throws Exception {
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		user.setLoginToken(777);
		userService.createUser(user);
		User tempUser = userService.findUserByEmail(user.getEmail());
		
		//Log In user
		Map<String, Integer> token = Map.of("token", 777);
		String context = objectMapper.writeValueAsString(token);
		MvcResult result = mockMvc.perform(post("/login/login-token").with(request -> {
			request.addHeader("credentials", "include"); return request;})
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
				.andExpect(status().isOk())
				.andReturn();
		
		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute("CurrentUser", tempUser.getUserId());
		System.out.println("--------------------------------------------------------------------");
		System.out.println(mockSession.getAttribute("CurrentUser"));
		
		Map<String, Object> sessionAttrs = new HashMap<String, Object>();
		sessionAttrs.put("CurrentUser", tempUser.getUserId());
		sessionAttrs.put("CurrentRole", tempUser.getUserRole());
		
		mockMvc.perform(get("/users/get/currentuser").session(mockSession))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("testFirstName"))
				.andExpect(jsonPath("$.lastName").value("testLastName"))
				.andExpect(jsonPath("$.email").value("test@email.com"))
				.andExpect(jsonPath("$.ssn").value(123456789))
				.andExpect(jsonPath("$.phoneNumber").value(1234567890));
		
	}
	
	@Test
	@Transactional
	public void testSuccessfulMortgagesByUserId() throws Exception {
		
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setEmail("test@email.com");
		user.setPassword("testPassword");
		user.setSSN(123456789);
		user.setPhoneNumber(1234567890L);
		userService.createUser(user);
		User testUser = userService.findUserByEmail(user.getEmail());
		Integer testUserId = testUser.getUserId();
		
		mockMvc.perform(get("/get/mortgages/{userId}", testUserId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
	

}

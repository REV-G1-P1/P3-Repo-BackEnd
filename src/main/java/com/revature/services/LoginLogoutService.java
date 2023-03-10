package com.revature.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.models.LoginResponse;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class LoginLogoutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(User user) {
        Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        if(tempUser.isPresent() && tempUser.get().isUserAccountLocked()) {
            loginResponse.setMessage("Account Locked");
            return loginResponse;
        }
        if (tempUser.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), tempUser.get().getPassword())) {
                tempUser.get().setFailedLoginAttempts(0);
                userRepository.save(tempUser.get());

                User newUser = new User();

                newUser.setFirstName(tempUser.get().getFirstName());
                newUser.setLastName(tempUser.get().getLastName());
                newUser.setAccountInformation(tempUser.get().getAccountInformation());
                newUser.setMortgageApplication(tempUser.get().getMortgageApplication());
                newUser.setTransactions(tempUser.get().getTransactions());
                newUser.setUserId(tempUser.get().getUserId());
                newUser.setAddress(tempUser.get().getAddress());
                newUser.setUserRole(tempUser.get().getUserRole());
                newUser.setSSN(tempUser.get().getSSN());
                newUser.setEmail(tempUser.get().getEmail());


                loginResponse.setUser(newUser);
                loginResponse.setMessage("Login Successful");
                return loginResponse;
            } else {
                tempUser.get().setFailedLoginAttempts(tempUser.get().getFailedLoginAttempts() + 1);
                if(tempUser.get().getFailedLoginAttempts() == 3) {
                    tempUser.get().setUserAccountLocked(true);
                }
                userRepository.save(tempUser.get());
                loginResponse.setMessage("Login Failed");
                return loginResponse;
            }
        } else {
            loginResponse.setMessage("Login Failed");
            return loginResponse;
        }
    }

    public LoginResponse loginToken(Integer token) {
        Optional<User> tempUser = userRepository.findUserByLoginToken(token);
        LoginResponse loginResponse = new LoginResponse();
        if (tempUser.isPresent()) {
            tempUser.get().setLoginToken(null);

            User newUser = new User();

            newUser.setFirstName(tempUser.get().getFirstName());
            newUser.setLastName(tempUser.get().getLastName());
            newUser.setAccountInformation(tempUser.get().getAccountInformation());
            newUser.setMortgageApplication(tempUser.get().getMortgageApplication());
            newUser.setTransactions(tempUser.get().getTransactions());
            newUser.setUserId(tempUser.get().getUserId());
            newUser.setAddress(tempUser.get().getAddress());
            newUser.setUserRole(tempUser.get().getUserRole());
            newUser.setSSN(tempUser.get().getSSN());
            newUser.setEmail(tempUser.get().getEmail());

            loginResponse.setUser(newUser);
            loginResponse.setMessage("Login Successful");
            return loginResponse;

        } else {
            loginResponse.setMessage("Login Failed");
            return loginResponse;
        }
    }

    public int getSessionAttributesById(String id) {
        List<byte[]> sessionsAttributes = new ArrayList<>(userRepository.getSessionAttributesById(id));
        List<Integer> currentSessions = new ArrayList<>();
        for(byte[] s : sessionsAttributes) {
            ObjectInput in;
            try {
                in = new ObjectInputStream(new ByteArrayInputStream(s));
                currentSessions.add(Integer.parseInt(in.readObject().toString()));
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currentSessions.get(0);
    }

    public String getSessionById(String sessionId) {
        String sessionAttributes = userRepository.getSessionById(sessionId);
        return sessionAttributes;
    }

    public void removeSessionById(String cookieId) {
        userRepository.removeSessionById(cookieId);
    }

    public void twoFactorAuthentication(User user) {

        final String ACCOUNT_SID = System.getenv("TwillioSID");
        final String AUTH_TOKEN = System.getenv("TwillioAuthToken");

        Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
        int randomToken = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
        if(tempUser.isPresent()) {
            tempUser.get().setLoginToken(randomToken);

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+1" + tempUser.get().getPhoneNumber()),
                    new com.twilio.type.PhoneNumber(System.getenv("TwillioPhoneNumber")),
                    "Verification Token: " + randomToken)
                .create();

            System.out.println(message.getSid());
        }
    }
}
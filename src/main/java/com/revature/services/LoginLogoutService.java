package com.revature.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class LoginLogoutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(User user) {
        Optional<User> tempUser = userRepository.findUserByEmail(user.getEmail());
        if (tempUser.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), tempUser.get().getPassword())) {
                return tempUser.get();
            } else {
                return null;
            }
        } else {
            return null;
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
    
}

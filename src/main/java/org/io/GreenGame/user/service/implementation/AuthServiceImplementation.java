package org.io.GreenGame.user.service.implementation;

import org.io.GreenGame.config.SecurityConfig;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.model.Role;
import org.io.GreenGame.user.model.Security;
import org.io.GreenGame.user.model.UserRegisterForm;
import org.io.GreenGame.user.repository.RoleRepository;
import org.io.GreenGame.user.repository.UserRepository;
import org.io.GreenGame.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

@Service
@ComponentScan
public class AuthServiceImplementation implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Boolean registerUser(UserRegisterForm userRegisterForm) {
        Long id = new Random().nextLong();
        LocalDateTime creationDate = LocalDateTime.now();

        while (userRepository.checkIfIdIsInDatabase(id) != 0) {
            id = new Random().nextLong();

        }
        Boolean doPasswordsMatch = Objects.equals(userRegisterForm.getPassword(), userRegisterForm.getRepeatedPassword());
        Long isIdInDatabase = userRepository.checkIfUsernameIsInDatabase(userRegisterForm.getUsername());
        Long isEmailInDatabase = userRepository.checkIfEmailIsInDatabase(userRegisterForm.getEmail());
        if (isIdInDatabase != 0 || isEmailInDatabase != 0 || !doPasswordsMatch) {
            return false;
        } else {
            String hashPw = SecurityConfig.passwordEncoder().encode(userRegisterForm.getPassword());
            Security security = new Security(creationDate, null, hashPw);
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = addUserRole();
            }
            GreenGameUser greenGameUser = new GreenGameUser(id, userRegisterForm.getUsername(), userRegisterForm.getEmail(), creationDate, creationDate, Collections.singletonList(role), security);
            //TODO: CREATE INVENTORY
            try {
                userRepository.save(greenGameUser);
            } catch (Exception ex) {
                return false;
            }
            return true;

        }

    }

    @Override
    public Boolean deleteUser(GreenGameUser greenGameUser) {
        if (userRepository.findById(greenGameUser.getId()).isPresent()) {
            userRepository.delete(greenGameUser);
            return true;
        } else return false;
    }

    @Override
    public Boolean changePassword(GreenGameUser greenGameUser, String oldPassword, String newPassword) {
        Boolean isOldPasswordCorrect = verifyPassword(greenGameUser, oldPassword);
        Boolean isNewPasswordSameAsOld = verifyPassword(greenGameUser, newPassword);
        if (!isOldPasswordCorrect || isNewPasswordSameAsOld) {
            return false;
        } else {
            greenGameUser.getSecurityData().setPasswordHash(SecurityConfig.passwordEncoder().encode(newPassword));
            userRepository.save(greenGameUser);
            return true;
        }
    }

    @Override
    public GreenGameUser getUserFromSession() {
        return userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private Boolean verifyPassword(GreenGameUser greenGameUser, String password) {
        return SecurityConfig.passwordEncoder().matches(password, greenGameUser.getSecurityData().getPasswordHash());
    }

    private Role addUserRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

}

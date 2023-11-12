package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.User;

public interface AuthService {
    Boolean registerUser(String username, String email, String password);
    Boolean deleteUser(User user);
    void changePassword(User user, String password);
    String activateTotp(User user);
    void deactivateTotp(User user);
    Boolean hasTotp(User user);
    Boolean authUser(String username, String password, String totp); // gives user cookie, callable by API
    Boolean verifyPassword(User user, String password);
    Boolean verifyTOTP(User user, String password);
    User getUserFromSession(Long userId); // ,  cookie); // Returns user if logged in, else null
}

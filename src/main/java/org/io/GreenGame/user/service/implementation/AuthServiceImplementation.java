package org.io.GreenGame.user.service.implementation;

import org.io.GreenGame.user.model.User;
import org.io.GreenGame.user.service.AuthService;

public class AuthServiceImplementation implements AuthService {
    @Override
    public Boolean registerUser(String username, String email, String password) {
        return null;
    }

    @Override
    public Boolean deleteUser(User user) {
        return null;
    }

    @Override
    public void changePassword(User user, String password) {

    }

    @Override
    public String activateTotp(User user) {
        return null;
    }

    @Override
    public void deactivateTotp(User user) {

    }

    @Override
    public Boolean hasTotp(User user) {
        return null;
    }

    @Override
    public Boolean authUser(String username, String password, String totp) {
        return null;
    }

    public Boolean verifyPassword(User user, String password) {
        return null;
    }

    public Boolean verifyTOTP(User user, String password) {
        return null;
    }

    @Override
    public User getUserFromSession(Long userId) {
        return null;
    }
}

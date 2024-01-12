package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.model.UserRegisterForm;

import java.util.List;

public interface AuthService {
    Boolean registerUser(UserRegisterForm userRegisterForm);
    Boolean deleteUser(GreenGameUser greenGameUser);
    Boolean changePassword(GreenGameUser greenGameUser, String oldPassword, String newPassword);
    GreenGameUser getUserFromSession();
    List<GreenGameUser> getAllUsersFromDatabase();
}

package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.GreenGameUser;
//import org.io.GreenGame.user.model.UserLoginForm;
import org.io.GreenGame.user.model.UserRegisterForm;

public interface AuthService {
    Boolean registerUser(UserRegisterForm userRegisterForm);
    Boolean deleteUser(GreenGameUser greenGameUser);
    Boolean changePassword(GreenGameUser greenGameUser, String oldPassword, String newPassword);
//    Boolean authUser(UserLoginForm userLoginForm); // gives user cookie, callable by API
    GreenGameUser getUserFromSession();
}

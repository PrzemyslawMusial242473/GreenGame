package org.io.GreenGame.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.io.GreenGame.user.model.ChangePasswordForm;
import org.io.GreenGame.user.model.UserRegisterForm;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class UserAPI {

    @Autowired
    AuthServiceImplementation authServiceImplementation;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("userRegisterForm", new UserRegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserSubmit(@ModelAttribute UserRegisterForm userRegisterForm, Model model) {
        model.addAttribute("userRegisterForm", userRegisterForm);
        if (authServiceImplementation.registerUser(userRegisterForm)) {
            model.addAttribute("registeredSuccessfully", "true");
            return "login";
        } else {
            model.addAttribute("registeredSuccessfully", "false");
            return "register";
        }
    }

    @GetMapping(value = "/login")
    public String loginUser() {
        log.info("getagain");
        return "login";

    }

    @GetMapping(value = "/secured/hello")
    public String getUserFromSession(Model model) {
        model.addAttribute("userData", authServiceImplementation.getUserFromSession());
        return "hello";
    }

    @GetMapping(value = "/secured/changePassword")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "changePassword";
    }

    @PostMapping(value = "/secured/changePassword")
    public String changePassword(@ModelAttribute ChangePasswordForm changePasswordForm, Model model) {
        model.addAttribute("changePasswordForm", changePasswordForm);
        if (authServiceImplementation.changePassword(authServiceImplementation.getUserFromSession(), changePasswordForm.getOldPassword(), changePasswordForm.getNewPassword())) {
            model.addAttribute("changedPasswordSuccessfuly", "true");
            return getUserFromSession(model);
        } else {
            model.addAttribute("changedPasswordSuccessfuly", "false");
            return changePassword(model);
        }
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(Model model) {
        if(authServiceImplementation.deleteUser( authServiceImplementation.getUserFromSession())){
            return "index";
        }
        else{
            return getUserFromSession(model);
        }
    }
}

package org.io.GreenGame.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAPI {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}

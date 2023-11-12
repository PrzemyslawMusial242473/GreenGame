package org.io.GreenGame.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestAPI {
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @GetMapping(value = "/test", produces = "text/plain")
    public String fetchAllUsers() {
        return "Test API :D";
    }
}

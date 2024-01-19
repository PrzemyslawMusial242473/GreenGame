package org.io.GreenGame.fight.controller;

import lombok.Getter;
import org.io.GreenGame.fight.service.EncounterService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Getter
@RestController
@RequestMapping("/secured/fight")
public class EncounterController {
//    @Autowired
//    private EncounterService service;

    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @GetMapping("/")
    public RedirectView navigate() {
        return new RedirectView("http://localhost:8081/");
    }

    @GetMapping("/HP")
    public int countHP() {
        return 3;
    }

    @GetMapping("/ID")
    public Long getIdOfLoggedUser() {
        return authServiceImplementation.getUserFromSession().getId();
    }

}

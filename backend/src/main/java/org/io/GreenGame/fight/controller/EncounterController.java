package org.io.GreenGame.fight.controller;

import lombok.Getter;
import org.io.GreenGame.fight.service.EncounterService;
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

    @GetMapping("/")
    public RedirectView navigate() {
        return new RedirectView("http://localhost:8081/");
    }

}

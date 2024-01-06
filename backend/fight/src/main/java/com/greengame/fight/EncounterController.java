package com.greengame.fight;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Getter
@RestController
public class EncounterController {
    private static final String hello = "Hello";

    @GetMapping("/navigate")
    public RedirectView naviagate()
    {
        return new RedirectView("http://localhost:8081/");
    }

    @GetMapping("/")
    public String infoForRedirect()
    {
        return "Type /navigate in URL to play!";
    }
}

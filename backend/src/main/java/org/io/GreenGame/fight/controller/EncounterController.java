package org.io.GreenGame.fight.controller;

import lombok.Getter;
import org.io.GreenGame.fight.service.EncounterService;
import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.service.InventoryServiceImplementation;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@RestController
@RequestMapping("/secured/fight")
public class EncounterController {

    @Autowired
    private AuthServiceImplementation authServiceImplementation;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InventoryServiceImplementation inventoryServiceImplementation;

    @GetMapping("/")
    public RedirectView navigate() {
        return new RedirectView("http://localhost:8081/");
    }

    @GetMapping("/HP")
    public int countHP() {
        int HP = 3;

        Inventory inventory = inventoryServiceImplementation.getUserInventory(authServiceImplementation.getUserFromSession().getId());
        if (!(inventory == null)){
            for(Item item: inventory.getItems())
            {
                HP += extractValue(item.getDescription());
            }
        }
        return HP;
    }

    @GetMapping("/ID")
    public Long getIdOfLoggedUser() {
        return authServiceImplementation.getUserFromSession().getId();
    }

    public int extractValue(String input) {
        String regex = "HP: (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String value = matcher.group(1);
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

}

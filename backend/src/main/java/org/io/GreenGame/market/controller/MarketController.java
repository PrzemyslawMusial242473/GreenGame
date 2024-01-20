package org.io.GreenGame.market.controller;

import org.io.GreenGame.market.service.MarketService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "market")
public class MarketController {
    private final MarketService marketService;
    private final AuthServiceImplementation authServiceImplementation;

    @Autowired
    public MarketController(MarketService marketService, AuthServiceImplementation authServiceImplementation) {
        this.marketService = marketService;

        this.authServiceImplementation = authServiceImplementation;
    }
    @GetMapping()
    @ResponseBody
    public String hello(){
        return "HELLO";
    }


    @GetMapping("/allOffers")
    public String getOffers(Model model){
        model.addAttribute("offers",marketService.getOffers(authServiceImplementation.getUserFromSession().getId()));
        model.addAttribute("money",marketService.getUserMoney(authServiceImplementation.getUserFromSession().getId()));
        return "market";
    }

    @GetMapping("/myAccount")
//    @ResponseBody
    public String getUser(Model model){
//        return marketService.marketUser(authServiceImplementation.getUserFromSession().getId());
//        model.addAttribute("inventory", marketService)
        return "myMarketAccount";
    }

    @GetMapping("/myOffers")
//    @ResponseBody
    public String getUserOffers(Model model){
//        return marketService.getMyOffers(authServiceImplementation.getUserFromSession().getId());
        model.addAttribute("myOffers",marketService.getMyOffers(authServiceImplementation.getUserFromSession().getId()));
        return "myOffers";
    }
    @PostMapping("/getOffer")
    public String getOffer(@RequestParam("description") String desc,
                           @RequestParam("price") double price,
                           @RequestParam("item") Long itemid){
        marketService.createOffer(authServiceImplementation.getUserFromSession().getId(),desc,price,itemid);
        return "redirect:/market/allOffers";
    }

    @PostMapping("/allOffers")
    public String getOffer(@RequestParam("key") String desc,
                           @RequestParam("item") int order,
                           Model model){
        switch (order){
            case 2: model.addAttribute("offers", marketService.getOffersFilteredPriceDesc(desc));
            break;
            case 3: model.addAttribute("offers", marketService.getOffersFilteredNameAsc(desc));
            break;
            case 4: model.addAttribute("offers", marketService.getOffersFilteredNameDesc(desc));
            break;
            default:model.addAttribute("offers", marketService.getOffersFilteredPriceAsc(desc));
        }
        model.addAttribute("sel0",order == 1);
        model.addAttribute("sel1",order == 2);
        model.addAttribute("sel2",order == 3);
        model.addAttribute("sel3",order == 4);

        model.addAttribute("key",desc);
        model.addAttribute("money",marketService.getUserMoney(authServiceImplementation.getUserFromSession().getId()));
        return "market";
    }

//    @PostMapping("/getBuy")
//    public String getBuy(@RequestParam("id") int cos){
//        marketService.buyItem((long) cos,authServiceImplementation.getUserFromSession().getId());
//        return "redirect:/market/allOffers";
//    }

    @PostMapping("/buy/{id}")
    public String buyOffer(@PathVariable Long id) {
        marketService.buyItem(id,authServiceImplementation.getUserFromSession().getId());
        return "redirect:/market/allOffers";
    }

    @PostMapping("/editOffer/{id}")
    public String modify(@PathVariable Long id, Model model){
        model.addAttribute("offer", marketService.getMarketOffer(id));
        return "changeOffer";
    }

    @PostMapping("/changeOffer/{id}")
    public String getChangeOffer(@PathVariable Long id,
                                 @RequestParam("newPrice") double price,
                                 @RequestParam("newDesc") String description
    ){
        try {
            marketService.changeOffer(authServiceImplementation.getUserFromSession().getId(), id, price, description);
            return "redirect:/market/myOffers";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    @PostMapping("/deleteOffer")
//    public String deleteOffer(@RequestParam("id") int cos){
//        try {
//            marketService.deleteOffer((long) cos);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return "redirect:/market";
//    }
    @PostMapping("/myAccount")
    public String marketAccount(){
        return "myMarketAccount";
    }


    @GetMapping("/addOffer")
    public String addOffer(Model model){
        model.addAttribute("items",marketService.getUserItems(authServiceImplementation.getUserFromSession().getId()));
        return "createOffer";
    }


//    @GetMapping("/buyItem")
//    public String buyItem(){
//        return "buyItem";
//    }
//    @PostMapping("/changeOffer/{id}")
//    public String changeOffer(@PathVariable Long id){
//        return "changeOffer";
//    }

    // TODO: Przy usuwaniu marketItem exist -> false
    @PostMapping("/deleteOffer/{id}")
    public String deleteOffer(@PathVariable Long id){
        try {
            marketService.deleteOffer(authServiceImplementation.getUserFromSession().getId(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/market/myOffers";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "errorPage";
    }
}

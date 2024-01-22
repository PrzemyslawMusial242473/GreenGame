package org.io.GreenGame.daily.controller;

import org.io.GreenGame.daily.service.DailyRewardServiceImpl;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/secured/daily")
public class DailyController {

    private DailyRewardServiceImpl dailyRewardService;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @Autowired
    public DailyController(DailyRewardServiceImpl dailyRewardService) {
        this.dailyRewardService = dailyRewardService;
    }

    @GetMapping("/getDaily")
    public ResponseEntity getDaily(){
        dailyRewardService.addDailyRewards(authServiceImplementation.getUserFromSession().getId());
        return ResponseEntity.ok().build();
    }

}

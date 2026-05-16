package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.service.UserBalanceService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BalanceController {

    private UserBalanceService userBalanceService;

    public BalanceController(UserBalanceService userBalanceService) {
        this.userBalanceService = userBalanceService;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        double amount = userBalanceService.getBalance(userId);
        return new Balance((float) amount);
    }
}
package com.jpmc.midascore.service;

import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    private UserRepository userRepository;

    public UserBalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public double getBalance(Long userId) {
        return userRepository.findById(userId).map(UserRecord::getBalance).orElse(0.0f);
    }
}
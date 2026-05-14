package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {
    private final RestTemplate restTemplate;
    private final String incentiveApiUrl = "http://localhost:8080/incentive";

    @Autowired
    public IncentiveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Incentive getIncentive(Transaction transaction) {
        try {
            return restTemplate.postForObject(incentiveApiUrl, transaction, Incentive.class);
        } catch (Exception e) {
            return new Incentive(0f);
        }
    }
}

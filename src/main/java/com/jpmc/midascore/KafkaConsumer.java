package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final List<Float> firstFourAmounts = new ArrayList<>();

    @KafkaListener(topics = "${general.kafka-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeTransaction(Transaction transaction) {
        logger.info("Received transaction: {}", transaction);
        logger.info("From: user_{}", transaction.getSenderId());
        logger.info("To: user_{}", transaction.getRecipientId());
        logger.info("Amount: ${}", transaction.getAmount());
    }
}
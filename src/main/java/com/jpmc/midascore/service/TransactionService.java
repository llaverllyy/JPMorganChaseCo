package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final IncentiveService incentiveService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              IncentiveService incentiveService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.incentiveService = incentiveService;
    }

    @KafkaListener(topics = "transactions", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void processTransaction(Transaction transaction) {
        if (transaction == null) return;

        logger.info("Processing transaction: {}", transaction);
        logger.info("From: user_{}", transaction.getSenderId());
        logger.info("To: user_{}", transaction.getRecipientId());
        logger.info("Amount: ${}", transaction.getAmount());

        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();
            UserRecord recipient = recipientOpt.get();

            if (sender.getBalance() >= transaction.getAmount()) {
                com.jpmc.midascore.foundation.Incentive incentive = incentiveService.getIncentive(transaction);
                float incentiveAmount = incentive != null ? incentive.getAmount() : 0f;

                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

                userRepository.save(sender);
                userRepository.save(recipient);

                TransactionRecord transactionRecord = new TransactionRecord();
                transactionRecord.setAmount((double) transaction.getAmount());
                transactionRecord.setIncentive((double) incentiveAmount);
                transactionRecord.setSender(sender);
                transactionRecord.setRecipient(recipient);
                transactionRepository.save(transactionRecord);
            }
        }
    }
}

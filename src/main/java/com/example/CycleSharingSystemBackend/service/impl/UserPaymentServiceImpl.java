package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.dto.PaymentRequestDto;
import com.example.CycleSharingSystemBackend.model.UserPayment;
import com.example.CycleSharingSystemBackend.repository.UserPaymentRepository;
import com.example.CycleSharingSystemBackend.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(UserPaymentServiceImpl.class);

    private final UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public Double getTotalEstimatedAmountForDate(LocalDate paymentDate) {
        logger.info("Fetching total estimated amount for date: {}", paymentDate);
        Double totalAmount = userPaymentRepository.getTotalEstimatedAmountForDate(paymentDate);
        logger.info("Total estimated amount for date {}: {}", paymentDate, totalAmount);
        return totalAmount;
    }

    @Override
    public CreatePaymentResponse createPaymentIntent(PaymentRequestDto paymentRequest) {
        return null;
    }

    @Override
    public double getestimatedAmount(Long id) {
        UserPayment userPayment= userPaymentRepository.findById(id).get();
        return userPayment.getEstimatedAmount();
    }
    @Override
    public void saveEstimatedAmount(PaymentRequestDto requestDto) {
        UserPayment userPayment = new UserPayment();
        userPayment.setId(userPayment.getId());
        userPayment.setEstimatedAmount(requestDto.getEstimatedAmount());
        userPaymentRepository.save(userPayment);
    }
}

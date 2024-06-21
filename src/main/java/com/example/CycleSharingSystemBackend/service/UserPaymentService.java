package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.dto.PaymentRequestDto;

import java.time.LocalDate;

public interface UserPaymentService {
    Double getTotalEstimatedAmountForDate(LocalDate paymentDate);
    CreatePaymentResponse createPaymentIntent(PaymentRequestDto paymentRequest);
    public double getestimatedAmount(Long id );
    void saveEstimatedAmount(PaymentRequestDto requestDto);
}

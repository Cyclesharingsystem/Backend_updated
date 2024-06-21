package com.example.CycleSharingSystemBackend.dto;

import lombok.*;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String id;
    private Long userId;
    private double estimatedAmount;


}

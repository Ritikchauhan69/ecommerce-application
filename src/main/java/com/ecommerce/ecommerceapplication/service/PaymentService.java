package com.ecommerce.ecommerceapplication.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerceapplication.entity.Order;
import com.ecommerce.ecommerceapplication.entity.Payment;
import com.ecommerce.ecommerceapplication.entity.PaymentMethod;
import com.ecommerce.ecommerceapplication.entity.PaymentStatus;
import com.ecommerce.ecommerceapplication.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment recordPayment(Order order, BigDecimal amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("No payment found for order id: " + orderId));
    }

}

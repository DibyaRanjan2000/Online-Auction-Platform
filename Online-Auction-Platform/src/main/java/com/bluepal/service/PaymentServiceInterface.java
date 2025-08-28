package com.bluepal.service;

import com.razorpay.RazorpayException;
import org.json.JSONObject;

import java.math.BigDecimal;

import com.bluepal.dto.PaymentDTO;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import java.math.BigDecimal;

public interface PaymentServiceInterface {

    /**
     * Creates a Razorpay order for a given purchase.
     *
     * @param purchaseId the ID of the purchase
     * @param amount the amount to be paid
     * @return a JSONObject containing order details
     * @throws RazorpayException if Razorpay order creation fails
     */
    JSONObject createRazorpayOrder(Long purchaseId, BigDecimal amount) throws RazorpayException;

    /**
     * Verifies Razorpay payment signature and saves the payment details if valid.
     *
     * @param orderId Razorpay order ID
     * @param paymentId Razorpay payment ID
     * @param signature Razorpay payment signature
     * @return PaymentDTO containing saved payment details
     * @throws RazorpayException if signature verification fails or any Razorpay-related issue occurs
     */
    PaymentDTO savePayment(String orderId, String paymentId, String signature) throws RazorpayException;
}

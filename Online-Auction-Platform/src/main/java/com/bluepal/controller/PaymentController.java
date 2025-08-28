package com.bluepal.controller;

import com.bluepal.dto.PaymentDTO;
import com.bluepal.service.PaymentService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentController {
	private final PaymentService paymentService;

	// Create Razorpay order
	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> req) {
		Long purchaseId = Long.valueOf(req.get("purchaseId").toString());
		BigDecimal amount = new BigDecimal(req.get("amount").toString());
		try {
			JSONObject orderJson = paymentService.createRazorpayOrder(purchaseId, amount);
			return ResponseEntity.ok(orderJson.toMap());
		} catch (RazorpayException e) {
			return ResponseEntity.badRequest().body("Order creation failed: " + e.getMessage());
		}
	}

	// Verify payment and save
	@PostMapping("/verify")
	public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> req) {
		try {
			PaymentDTO dto = paymentService.savePayment(req.get("razorpay_order_id"), req.get("razorpay_payment_id"),
					req.get("razorpay_signature"));
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Payment verification failed: " + e.getMessage());
		}
	}
}

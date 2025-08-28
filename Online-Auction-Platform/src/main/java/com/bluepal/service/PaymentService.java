package com.bluepal.service;

import com.bluepal.dto.PaymentDTO;
import com.bluepal.entity.Payment;
import com.bluepal.entity.Purchase;
import com.bluepal.repo.PaymentRepository;
import com.bluepal.repo.PurchaseRepository;
import com.razorpay.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
	private final RazorpayClient razorpayClient;
	private final String razorpaySecret;
	private final PurchaseRepository purchaseRepo;
	private final PaymentRepository paymentRepo;

	// Create a Razorpay order tied to a purchase
	public JSONObject createRazorpayOrder(Long purchaseId, BigDecimal amount) throws RazorpayException {
		Purchase purchase = purchaseRepo.findById(purchaseId)
				.orElseThrow(() -> new RuntimeException("Purchase not found"));

		JSONObject options = new JSONObject();
		options.put("amount", amount.multiply(BigDecimal.valueOf(100))); // in paise
		options.put("currency", "INR");
		options.put("receipt", "rcpt_" + purchaseId);
		options.put("payment_capture", 1);

		Order order = razorpayClient.orders.create(options);
		// Persist order ID in purchase if needed
		purchase.setRazorpayOrderId(order.get("id"));
		purchaseRepo.save(purchase);

		return order.toJson();
	}

	// Verify Razorpay signature and save payment
//    @Transactional
//    public PaymentDTO savePayment(String orderId, String paymentId, String signature) throws RazorpayException {
//        JSONObject payload = new JSONObject();
//        payload.put("razorpay_order_id", orderId);
//        payload.put("razorpay_payment_id", paymentId);
//        payload.put("razorpay_signature", signature);
//
//        boolean isValid = Utils.verifyPaymentSignature(payload, razorpaySecret);
//        if (!isValid) {
//            throw new RuntimeException("Payment signature verification failed");
//        }
//
//        Purchase purchase = purchaseRepo.findByRazorpayOrderId(orderId)
//        		
//        		
//            .orElseThrow(() -> new RuntimeException("Purchase not found"));
//
//        if (paymentRepo.findByPurchaseId(purchase.getId()).isPresent()) {
//            throw new RuntimeException("Payment already exists for this purchase");
//        }
//
//        Payment payment = Payment.builder()
//            .purchase(purchase)
//            .amount(purchase.getFinalPrice())
//            .method("RAZORPAY")
//            .status("SUCCESS")
//            .paidAt(Instant.now())
//            .build();
//
//        Payment saved = paymentRepo.save(payment);
//        return new PaymentDTO(saved.getId(), purchase.getId(), saved.getAmount(),
//                               saved.getMethod(), saved.getStatus(), saved.getPaidAt());
//    }

	// Verify Razorpay signature and save payment
	@Transactional
	public PaymentDTO savePayment(String orderId, String paymentId, String signature) throws RazorpayException {
		log.info("🔹 Verifying Razorpay payment. orderId={}, paymentId={}, signature={}", orderId, paymentId,
				signature);

		JSONObject payload = new JSONObject();
		payload.put("razorpay_order_id", orderId);
		payload.put("razorpay_payment_id", paymentId);
		payload.put("razorpay_signature", signature);

		boolean isValid = Utils.verifyPaymentSignature(payload, razorpaySecret);
		log.info("🔹 Signature verification result: {}", isValid);

		if (!isValid) {
			throw new RuntimeException("❌ Payment signature verification failed");
		}

		// Find purchase by orderId
		Purchase purchase = purchaseRepo.findByRazorpayOrderId(orderId)
				.orElseThrow(() -> new RuntimeException("❌ Purchase not found for orderId=" + orderId));

		// Prevent duplicate payment
		if (paymentRepo.findByPurchaseId(purchase.getId()).isPresent()) {
			throw new RuntimeException("⚠️ Payment already exists for this purchase");
		}

		// Save payment into payments table (✅ store Razorpay details here)
		Payment payment = Payment.builder().purchase(purchase).amount(purchase.getFinalPrice()).method("RAZORPAY")
				.status("SUCCESS").paidAt(Instant.now()).razorpayOrderId(orderId).razorpayPaymentId(paymentId)
				.razorpaySignature(signature).build();

		Payment saved = paymentRepo.save(payment);

		log.info("✅ Payment saved. paymentId={}, purchaseId={}, amount={}", saved.getId(), purchase.getId(),
				saved.getAmount());

		return new PaymentDTO(saved.getId(), purchase.getId(), saved.getAmount(), saved.getMethod(), saved.getStatus(),
				saved.getPaidAt(), saved.getRazorpayOrderId(), saved.getRazorpayPaymentId(),
				saved.getRazorpaySignature());
	}

}

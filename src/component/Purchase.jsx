import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import apiService from "../apiservices/apiUrls";
import "./Purchase.css";

const Purchase = () => {
  const { id } = useParams();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [sdkLoaded, setSdkLoaded] = useState(false);
  const [showRating, setShowRating] = useState(false);
  const [rating, setRating] = useState(0);
  let navigate = useNavigate();

  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.async = true;
    script.onload = () => setSdkLoaded(true);
    script.onerror = () => console.error("❌ Failed to load Razorpay SDK");
    document.body.appendChild(script);
  }, []);

  useEffect(() => {
    const fetchItem = async () => {
      try {
        const response = await apiService.get(`/items/${id}`);
        setItem(response);
      } catch (error) {
        console.error("❌ Error fetching item:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchItem();
  }, [id]);

  const handlePayment = async () => {
    try {
      if (!sdkLoaded || !window.Razorpay) {
        alert("⚠️ Razorpay SDK not yet loaded. Please wait.");
        return;
      }

      const orderResponse = await apiService.post(`/payments/create-order`, {
        purchaseId: id,
        amount: item.startingPrice,
      });

      const order = orderResponse.data || orderResponse;

      const options = {
        key: "rzp_test_R6s6aVW39Oqimg",
        amount: order.amount,
        currency: order.currency,
        name: "Live Auction",
        description: `Purchase of ${item?.name}`,
        order_id: order.id,
        handler: async function (response) {
          try {
            await apiService.post(`/payments/verify`, {
              razorpay_order_id: response.razorpay_order_id,
              razorpay_payment_id: response.razorpay_payment_id,
              razorpay_signature: response.razorpay_signature,
            });
            alert("🎉 Payment Successful & Verified!");
          } catch (verifyError) {
            console.error("❌ Verification failed:", verifyError);
            // 👇 Show rating instead of immediate navigate
            setShowRating(true);
          }
        },
        prefill: {
          name: "Auction User",
          email: "user@example.com",
          contact: "9999999999",
        },
        theme: { color: "#3399cc" },
      };

      const rzp = new window.Razorpay(options);
      rzp.open();
    } catch (err) {
      console.error("❌ Error in payment:", err);
    }
  };

  const submitRating = async () => {
    try {
      await apiService.post(`/ratings`, {
        itemId: id,
        rating: rating,
      });
      alert("⭐ Thanks for your rating!");
    } catch (err) {
      console.error("❌ Failed to submit rating:", err);
    } finally {
      setShowRating(false);
      navigate("/my-bids"); // redirect after rating
    }
  };

  if (loading) return <div>Loading purchase details...</div>;
  if (!item) return <div>❌ Item not found</div>;

  return (
    <div className="purchase-container">
      <h2>Purchase Item</h2>
      <div className="item-card">
        <h3>{item.name}</h3>
        <p>{item.description}</p>
        <p>
          <strong>Final Price:</strong> ₹{item.startingPrice}
        </p>
        <button onClick={handlePayment} disabled={!sdkLoaded}>
          💳 Pay with Razorpay
        </button>
      </div>

      {/* ⭐ Rating Modal */}
      {showRating && (
        <div className="rating-modal">
          <div className="rating-card">
            <h3>⭐ Rate Your Purchase</h3>
            <div className="stars">
              {[1, 2, 3, 4, 5].map((star) => (
                <span
                  key={star}
                  className={star <= rating ? "star filled" : "star"}
                  onClick={() => setRating(star)}
                >
                  ★
                </span>
              ))}
            </div>
            <button onClick={submitRating} disabled={rating === 0}>
              Submit Rating
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Purchase;

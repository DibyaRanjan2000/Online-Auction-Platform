// src/components/ItemDetails.jsx
import React, { useEffect, useState, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import apiService from "../apiservices/apiUrls";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import "./ItemDetails.css";

const ItemDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [item, setItem] = useState(null);
  const [bidAmount, setBidAmount] = useState("");
  const [message, setMessage] = useState("");
  const [highBid, setHighBid] = useState(null);
  const [loading, setLoading] = useState(true);

  const [stompClient, setStompClient] = useState(null);

  // ⏳ Timer state (default 3 minutes = 180s)
  const [timeLeft, setTimeLeft] = useState(180);

  // Format seconds into mm:ss
  const formatTime = (seconds) => {
    const m = Math.floor(seconds / 60)
      .toString()
      .padStart(2, "0");
    const s = (seconds % 60).toString().padStart(2, "0");
    return `${m}:${s}`;
  };

  // Timer countdown effect
  useEffect(() => {
    if (timeLeft <= 0) return;
    const timer = setInterval(() => {
      setTimeLeft((prev) => prev - 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [timeLeft]);

  // Fetch item details
  const fetchItem = useCallback(async () => {
    try {
      const response = await apiService.get(`/items/${id}`);
      setItem(response);
    } catch (error) {
      console.error("❌ Error fetching item details:", error);
    }
  }, [id]);

  // Fetch highest bid (fallback)
  const fetchHighestBid = useCallback(async () => {
    try {
      const response = await apiService.get(`/bids/items/${id}/top`);
      setHighBid(response);
    } catch (error) {
      console.error("❌ Error fetching highest bid:", error);
    }
  }, [id]);

  // Setup WebSocket connection
  useEffect(() => {
    const sock = new SockJS("http://localhost:8081/ws-auction");
    const client = new Client({
      webSocketFactory: () => sock,
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
    });

    client.onConnect = () => {
      console.log("✅ Connected to WebSocket");

      // Subscribe to bid updates for this item
      client.subscribe(`/topic/auction/${id}`, (msg) => {
        const bidUpdate = JSON.parse(msg.body);
        console.log("📢 Live bid update:", bidUpdate);
        setHighBid(bidUpdate); // Update state with real-time data
      });
    };

    client.onStompError = (frame) => {
      console.error("❌ STOMP Error:", frame);
    };

    client.activate();
    setStompClient(client);

    return () => {
      client.deactivate();
    };
  }, [id]);

  // Initial load
  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      await Promise.all([fetchItem(), fetchHighestBid()]);
      setLoading(false);
    };
    loadData();
  }, [fetchItem, fetchHighestBid]);

  // Place a bid
  const placeBid = async () => {
    if (!bidAmount) {
      setMessage("⚠️ Bid amount cannot be empty");
      return;
    }

    try {
      const response = await apiService.post(`/bids/items/${id}`, {
        amount: Number(bidAmount),
      });

      console.log("✅ Bid response:", response);
      setMessage("✅ Bid placed successfully!");
      setBidAmount("");
      navigate(`/purchase/${id}`, {
        state: { success: true, msg: "✅ Bid placed successfully!" },
      });
      // Live WS will update highest bid automatically
    } catch (error) {
      console.error("❌ Error placing bid:", error);
      setMessage("❌ Failed to place bid. Try again.");
    }
  };

  if (loading) {
    return (
      <div className="master-container">
        <div className="loading">Loading item details...</div>
      </div>
    );
  }

  if (!item) {
    return (
      <div className="master-container">
        <div className="error">Item not found!</div>
      </div>
    );
  }

  return (
    <div className="master-container">
      <div className="item-details-container">
        <div className="item-image-wrapper">
          <img
            src={item.imageUrl || "https://via.placeholder.com/600x400"}
            alt={item.name}
          />
        </div>

        <div className="item-info">
          <h2>{item.name}</h2>
          <p className="desc">{item.description}</p>
          <p>
            <strong>Starting Price:</strong> ${item.startingPrice}
          </p>
          <p>
            <strong>Highest Bid:</strong> ${highBid?.highest ?? "No bids yet"}
          </p>
          <p>
            <strong>Highest Bidder:</strong> {highBid?.highestBidder ?? "N/A"}
          </p>
          <p>{/* <strong>Auction ends at:</strong> {item.auctionEnd} */}</p>

          {/* ⏳ Timer (3 min default) */}
          <p
            style={{
              fontSize: "18px",
              fontWeight: "bold",
              color: timeLeft > 0 ? "#e63946" : "#6c757d",
              backgroundColor: timeLeft > 0 ? "#fff3cd" : "#f8f9fa",
              padding: "8px 12px",
              borderRadius: "8px",
              display: "inline-block",
              marginTop: "10px",
            }}
          >
            <strong>Time Left:</strong>{" "}
            {timeLeft > 0 ? formatTime(timeLeft) : "⏰ Auction ended"}
          </p>

          <h3>Place Your Bid</h3>
          <div className="bid-input-group">
            <input
              // type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              placeholder="Enter your bid amount"
            />
            <button onClick={placeBid}>💰 Place Bid</button>
          </div>

          {message && (
            <p
              className={`message ${
                message.includes("successfully")
                  ? "success"
                  : message.includes("Failed")
                  ? "error"
                  : "warning"
              }`}
            >
              {message}
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ItemDetails;

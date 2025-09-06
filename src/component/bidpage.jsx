import React, { useState } from "react";
import "./bidpage.css";

const BidPage = () => {
  const [bidAmount, setBidAmount] = useState("");

  // Example auction item (replace with props or API data)
  const item = {
    id: 1,
    name: "Antique Gold Coin",
    description: "Rare gold coin from 18th century. Excellent condition.",
    startingPrice: 500,
    highestBid: 1200,
    auctionEndTime: "2025-08-20 18:00",
    imageUrl: "https://images.unsplash.com/photo-1614281882404-d8c73ca5683d",
  };

  const handleBid = (e) => {
    e.preventDefault();
    if (!bidAmount) return alert("Please enter your bid amount!");
    alert(`Your bid of $${bidAmount} has been placed successfully!`);
    setBidAmount("");
  };

  return (
    <div className="bid-page">
      <div className="bid-card">
        <img src={item.imageUrl} alt={item.name} />
        <h2>{item.name}</h2>
        <p>{item.description}</p>
        <p><strong>Starting Price:</strong> ${item.startingPrice}</p>
        <p><strong>Current Highest Bid:</strong> ${item.highestBid}</p>
        <p><strong>Auction Ends:</strong> {item.auctionEndTime}</p>

        <form className="bid-form" onSubmit={handleBid}>
          <label>Enter Your Bid</label>
          <input
            type="number"
            value={bidAmount}
            onChange={(e) => setBidAmount(e.target.value)}
            placeholder="Enter bid amount"
            min={item.highestBid + 1}
            required
          />
          <button type="submit">Place Bid</button>
        </form>
      </div>
    </div>
  );
};

export default BidPage;

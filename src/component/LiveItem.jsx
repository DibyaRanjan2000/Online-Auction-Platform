import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import apiService from "../apiservices/apiUrls";
import "./LiveItems.css";

const LiveItems = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    debugger;
    apiService
      .get("/items/live")
      .then((response) => {
        if (response.content && response.content.length > 0) {
          setItems(response.content);
        } else {
          setItems([]);
        }
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to fetch live items. Please try again later.");
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div className="loading-spinner"></div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (items.length === 0) {
    return <div className="no-items-message">No live items available at the moment.</div>;
  }

  return (
    <div className="live-items-container">
      <h2 className="page-title">🔥 Live Auction Items 🔥</h2>
      <div className="items-grid">
        {items.map((item) => (
          <div key={item.id} className="item-card">
            <div className="image-wrapper">
              <img src={item?.imageUrl} alt={item.name} className="item-image" />
              <span className="live-badge">LIVE</span>
            </div>
            <div className="item-details">
              <h3>{item.name}</h3>
              <p className="price">Starting at: ₹{item.startingPrice}</p>
              <Link className="view-btn" to={`/item/${item.id}`}>
                View Details
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default LiveItems;

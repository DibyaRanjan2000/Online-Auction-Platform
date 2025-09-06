import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./narbar.css";
import apiService from "../apiservices/apiUrls";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [showSidebar, setShowSidebar] = useState(false);
  const [showLogoutPopup, setShowLogoutPopup] = useState(false);
  const [showHistoryModal, setShowHistoryModal] = useState(false); 
  const [purchaseHistory, setPurchaseHistory] = useState([]);

  const navigate = useNavigate();
  const token = localStorage.getItem("authToken");

  // ✅ Fetch history
  async function historyData() {
    try {
      let response = await apiService.get("/purchase/history");
      setPurchaseHistory(response);
    } catch (error) {
      console.error("Error fetching history:", error);
    }
  }

  // ✅ Prevent background scroll when any popup is open
  useEffect(() => {
    const isPopupOpen = showSidebar || showLogoutPopup || showHistoryModal;
    if (isPopupOpen) {
      document.body.classList.add("no-scroll");
    } else {
      document.body.classList.remove("no-scroll");
    }
  }, [showSidebar, showLogoutPopup, showHistoryModal]);

  useEffect(() => {
    historyData();
  }, []);

  function handleLoginClick() {
    navigate("/login");
  }

  function confirmLogout() {
    localStorage.removeItem("authToken");
    setShowLogoutPopup(false);
    setShowSidebar(false);
    navigate("/");
    window.location.reload();
  }

  return (
    <>
      <nav className="navbar">
        {/* Logo */}
        <div className="navbar-logo">⚡ AuctionX</div>

        {/* Links */}
        <ul className={`navbar-links ${isOpen ? "open" : ""}`}>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/live-items">Live Auctions</Link></li>
          <li><Link to="/my-bids">Bid History</Link></li>
          <li><Link to="/contact">Contact</Link></li>
        </ul>

        {/* Actions */}
        <div className="navbar-actions">
          {!token ? (
            <button className="login-btn" onClick={handleLoginClick}>Login</button>
          ) : (
            <img
              src="https://i.pravatar.cc/40"
              alt="Profile"
              className="profile-avatar"
              onClick={() => setShowSidebar(true)}
            />
          )}
        </div>

        {/* Hamburger for mobile */}
        <div
          className={`hamburger ${isOpen ? "active" : ""}`}
          onClick={() => setIsOpen(!isOpen)}
        >
          <span></span><span></span><span></span>
        </div>
      </nav>

      {/* ✅ Sidebar Drawer */}
      {showSidebar && (
        <div className="sidebar-overlay" onClick={() => setShowSidebar(false)}>
          <div className="sidebar" onClick={(e) => e.stopPropagation()}>
            <h3 className="sidebar-title">⚡ Menu</h3>
            <ul className="sidebar-links">
              <li onClick={() => { navigate("/admin"); setShowSidebar(false); }}>Admin</li>
              <li onClick={() => setShowLogoutPopup(true)}>Logout</li>
              <li onClick={() => setShowHistoryModal(true)}>📜 Details</li>
            </ul>
          </div>
        </div>
      )}

      {/* ✅ Logout Popup */}
      {showLogoutPopup && (
        <div className="popup-overlay">
          <div className="popup">
            <h3>Logout Confirmation</h3>
            <p>Are you sure you want to logout?</p>
            <div className="popup-buttons">
              <button className="cancel-btn" onClick={() => setShowLogoutPopup(false)}>Cancel</button>
              <button className="logout-btn" onClick={confirmLogout}>Logout</button>
            </div>
          </div>
        </div>
      )}

      {/* ✅ History Modal with Table */}
      {showHistoryModal && (
        <div className="popup-overlay">
          <div className="popup history-popup">
            <h3>Buyer Details</h3>
            <table className="history-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Item</th>
                  <th>Buyer</th>
                  <th>Price</th>
                  <th>Purchased At</th>
                </tr>
              </thead>
              <tbody>
                {purchaseHistory.map((purchase) => (
                  <tr key={purchase.id}>
                    <td>{purchase.id}</td>
                    <td>{purchase.itemName || "N/A"}</td>
                    <td>{purchase.buyerUsername || "Anonymous"}</td>
                    <td>₹{purchase.finalPrice?.toLocaleString()}</td>
                    <td>{new Date(purchase.purchasedAt).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="popup-buttons">
              <button className="cancel-btn" onClick={() => setShowHistoryModal(false)}>Close</button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

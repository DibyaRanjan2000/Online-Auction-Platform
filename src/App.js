import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./component/landing";
import Register from "./component/Register";
import Login from "./component/login";
import BidPage from "./component/bidpage";
import LiveItems from "./component/LiveItem";
import ItemDetails from "./component/ItemsDetails";
import Purchase from "./component/Purchase";
import Navbar from "./component/navbar";
import ProtectedRoute from "./component/ProtectedRoute"; // ✅ import
import PurchaseHistory from "./component/PurchaseHistory";
import ContactPage from "./component/ContactPage";
import AdminDashboard from "./component/AdminDashboard";

function App() {
  return (
    <Router>
      <div>
        <Navbar />

        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<Home />} />  {/* ✅ Landing always public */}
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/contact" element={<ContactPage />} />

          {/* Protected Routes */}
          <Route
            path="/bidpage"
            element={
              <ProtectedRoute>
                <BidPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/live-items"
            element={
              <ProtectedRoute>
                <LiveItems />
              </ProtectedRoute>
            }
          />
          <Route
            path="/item/:id"
            element={
              <ProtectedRoute>
                <ItemDetails />
              </ProtectedRoute>
            }
          />
          <Route
            path="/purchase/:id"
            element={
              <ProtectedRoute>
                <Purchase />
              </ProtectedRoute>
            }
          />

           <Route
            path="/my-bids"
            element={
              <ProtectedRoute>
                <PurchaseHistory />
              </ProtectedRoute>
            }
          />


           <Route
            path="/admin"
            element={
              <ProtectedRoute>
                <AdminDashboard />
              </ProtectedRoute>
            }
          />

          
        </Routes>
      </div>
    </Router>
  );
}

export default App;

import React, { useState } from "react";
import { FaPhoneAlt, FaEnvelope, FaMapMarkerAlt, FaFacebook, FaInstagram, FaLinkedin } from "react-icons/fa";
import "./contactPage.css";

export default function ContactPage() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobile: "",
    state: "",
    material: "",
  });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitted(true);
    setFormData({ name: "", email: "", mobile: "", state: "", material: "" });
    setTimeout(() => setSubmitted(false), 3000);
  };

  return (
    <div className="contact-container">
      {/* Left Side - Form */}
      <div className="contact-form-wrapper">
        <h1 className="form-title">CONTACT US</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label>Name:</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Enter Your Name"
                required
              />
            </div>
            <div className="form-group">
              <label>Email Id:</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter Email Id"
                required
              />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Mobile No.:</label>
              <input
                type="text"
                name="mobile"
                value={formData.mobile}
                onChange={handleChange}
                placeholder="Enter Mobile Number"
                required
              />
            </div>
            <div className="form-group">
              <label>State:</label>
              <input
                type="text"
                name="state"
                value={formData.state}
                onChange={handleChange}
                placeholder="Enter State"
                required
              />
            </div>
          </div>

          <div className="form-group">
            <label>Interested Material:</label>
            <input
              type="text"
              name="material"
              value={formData.material}
              onChange={handleChange}
              placeholder="Interested Material"
            />
          </div>

          <button type="submit" className="submit-btn">Submit</button>
        </form>

        {submitted && (
          <p className="success-msg">✅ Thank you! Your response has been submitted.</p>
        )}
      </div>

      {/* Right Side - Info */}
      <div className="contact-info-wrapper">
        <div className="contact-info">
          <h2>Hi! We are always here to help you.</h2>

          <div className="info-box">
            <FaPhoneAlt className="icon" /> <strong>Phone No.:</strong>
            <p>+91 8976702315 / +91 7400056461 <br /> +91 9320445534 / +91 8097593948</p>
          </div>

          <div className="info-box">
            <FaEnvelope className="icon" /> <strong>Email Id:</strong>
            <p>sc@auctionx.com</p>
          </div>

          <div className="info-box">
            <FaMapMarkerAlt className="icon" /> <strong>Address:</strong>
            <p>702, Opal Square IT Park, Wagle Estate, Padwal Nagar, <br /> Thane West, Mumbai, Maharashtra, 400604, India</p>
          </div>

          <div className="socials">
            <p>Connect with us:</p>
            <div className="social-icons">
              <FaFacebook /> <FaInstagram /> <FaLinkedin />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

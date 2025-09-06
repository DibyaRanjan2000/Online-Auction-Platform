// import React from "react";
// import { useForm } from "react-hook-form";
// import { Link } from "react-router-dom";
// import "./login.css";

// export default function Login() {
//   const {
//     register,
//     handleSubmit,
//     formState: { errors },
//   } = useForm();

//   const onSubmit = (data) => {
//     console.log("Login Data:", data);
//     alert("✅ Login Successful!");
//   };

//   return (
//     <div className="login-page">
//       <div className="login-card">
//         <h2>🔐 Welcome Back</h2>
//         <p className="subtitle">Login to continue your journey</p>

//         <form onSubmit={handleSubmit(onSubmit)}>
//           {/* Username */}
//           <div className="input-group">
//             <label>Username</label>
//             <input
//               type="text"
//               {...register("username", { required: "Username is required" })}
//               placeholder="Enter your username"
//             />
//             {errors.username && <p className="error">{errors.username.message}</p>}
//           </div>

//           {/* Password */}
//           <div className="input-group">
//             <label>Password</label>
//             <input
//               type="password"
//               {...register("password", { required: "Password is required" })}
//               placeholder="Enter your password"
//             />
//             {errors.password && <p className="error">{errors.password.message}</p>}
//           </div>

//           <button type="submit" className="login-btn">Login</button>
//         </form>

//         {/* Footer Links */}
//         <p className="extra-links">
//           Don’t have an account? <Link to="/register">Register</Link>
//         </p>
//       </div>
//     </div>
//   );
// }
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";  // useNavigate instead of useHistory
import axios from "axios";
import "./login.css";

export default function Login() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [loginError, setLoginError] = useState("");
  const navigate = useNavigate();  // useNavigate hook

  // Handling form submission
  const onSubmit = async (data) => {
    try {
      // Send login request to the backend
      const response = await axios.post('http://localhost:8081/auth/login', {
        username: data.username,
        password: data.password
      });

      // Assuming the response contains the JWT token
      const { token } = response.data;

      // Save the token in localStorage
      localStorage.setItem("authToken", token);

      // Redirect to the dashboard (or another page based on your app's flow)
      navigate("/live-items");  // use navigate() instead of history.push()
    } catch (error) {
      // Handle error during login
      setLoginError("Invalid username or password. Please try again.");
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <h2>🔐 Welcome Back</h2>
        <p className="subtitle">Login to continue your journey</p>

        {/* Display login error */}
        {loginError && <p className="error">{loginError}</p>}

        <form onSubmit={handleSubmit(onSubmit)}>
          {/* Username */}
          <div className="input-group">
            <label>Username</label>
            <input
              type="text"
              {...register("username", { required: "Username is required" })}
              placeholder="Enter your username"
            />
            {errors.username && <p className="error">{errors.username.message}</p>}
          </div>

          {/* Password */}
          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              {...register("password", { required: "Password is required" })}
              placeholder="Enter your password"
            />
            {errors.password && <p className="error">{errors.password.message}</p>}
          </div>

          {/* Submit Button */}
          <button type="submit" className="login-btn">Login</button>
        </form>

        {/* Footer Links */}
        <p className="extra-links">
          Don’t have an account? <Link to="/register">Register</Link>
        </p>
      </div>
    </div>
  );
}

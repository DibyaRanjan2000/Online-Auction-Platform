// import React from "react";
// import { useForm } from "react-hook-form";
// import "./Register.css";
// import { Link } from "react-router-dom";

// export default function Register() {
//   const {
//     register,
//     handleSubmit,
//     formState: { errors },
//   } = useForm();

//   const onSubmit = (data) => {
//     console.log("Register Data:", data);
//     alert("✅ Registration Successful!");
//   };

//   return (
//     <div className="register-container">
//       <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
//         <h2>Create Account</h2>

//         {/* Username */}
//         <label>Username</label>
//         <input
//           type="text"
//           {...register("username", { required: "Username is required" })}
//           placeholder="Enter your username"
//         />
//         {errors.username && <p className="error">{errors.username.message}</p>}

//         {/* Role */}
//         <label>Role</label>
//         <select {...register("role", { required: "Role is required" })}>
//           <option value="">-- Select Role --</option>
//           <option value="BUYER">Buyer</option>
//           <option value="SELLER">Seller</option>
//           <option value="ADMIN">Admin</option>
//         </select>
//         {errors.role && <p className="error">{errors.role.message}</p>}

//         {/* Email */}
//         <label>Email</label>
//         <input
//           type="email"
//           {...register("email", {
//             required: "Email is required",
//             pattern: { value: /\S+@\S+\.\S+/, message: "Invalid email" },
//           })}
//           placeholder="Enter your email"
//         />
//         {errors.email && <p className="error">{errors.email.message}</p>}

//         {/* Password */}
//         <label>Password</label>
//         <input
//           type="password"
//           {...register("password", {
//             required: "Password is required",
//             minLength: { value: 6, message: "Min length is 6" },
//           })}
//           placeholder="Enter your password"
//         />
//         {errors.password && <p className="error">{errors.password.message}</p>}

//         <button type="submit">Register</button>

//         <p className="login-link">
//           Already have an account? <Link to="/login">Login here</Link>
//         </p>
//       </form>
//     </div>
//   );
// }

// import React, { useState } from "react";
// import { useForm } from "react-hook-form";
// import { Link, useNavigate } from "react-router-dom";
// import axios from "axios";
// import "./Register.css";

// export default function Register() {
//   const {
//     register,
//     handleSubmit,
//     formState: { errors },
//   } = useForm();
//   const [registerError, setRegisterError] = useState(null);
//   const navigate = useNavigate();

//   const onSubmit = async (data) => {
//     try {
//       // Send the registration data to the backend
//       await axios.post("http://localhost:8081/auth/register", {
//         username: data.username,
//         email: data.email,
//         password: data.password,
//         role: data.role, // Optional, depending on your registration model
//       });

//       // If registration is successful
//       alert("✅ Registration Successful!");
//       navigate("/login"); // Redirect to login page after successful registration
//     } catch (error) {
//       // If an error occurs during registration (e.g., username already taken)
//       if (error.response && error.response.data) {
//         setRegisterError(error.response.data); // Display the error message
//       } else {
//         setRegisterError("Something went wrong. Please try again.");
//       }
//     }
//   };

//   return (
//     <div className="register-container">
//       <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
//         <h2>Create Account</h2>

//         {/* Username */}
//         <label>Username</label>
//         <input
//           type="text"
//           {...register("username", { required: "Username is required" })}
//           placeholder="Enter your username"
//         />
//         {errors.username && <p className="error">{errors.username.message}</p>}

//         {/* Role */}
//         <label>Role</label>
//         <select {...register("role", { required: "Role is required" })}>
//           <option value="">-- Select Role --</option>
//           <option value="BUYER">Buyer</option>
//           <option value="SELLER">Seller</option>
//           <option value="ADMIN">Admin</option>
//         </select>
//         {errors.role && <p className="error">{errors.role.message}</p>}

//         {/* Email */}
//         <label>Email</label>
//         <input
//           type="email"
//           {...register("email", {
//             required: "Email is required",
//             pattern: { value: /\S+@\S+\.\S+/, message: "Invalid email" },
//           })}
//           placeholder="Enter your email"
//         />
//         {errors.email && <p className="error">{errors.email.message}</p>}

//         {/* Password */}
//         <label>Password</label>
//         <input
//           type="password"
//           {...register("password", {
//             required: "Password is required",
//             minLength: { value: 6, message: "Min length is 6" },
//           })}
//           placeholder="Enter your password"
//         />
//         {errors.password && <p className="error">{errors.password.message}</p>}

//         {/* Display error message */}
//         {registerError && <p className="error">{registerError}</p>}

//         <button type="submit">Register</button>

//         <p className="login-link">
//           Already have an account? <Link to="/login">Login here</Link>
//         </p>
//       </form>
//     </div>
//   );
// }

import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Register.css";

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const [registerError, setRegisterError] = useState(null);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      // Send the registration data to the backend
      await axios.post("http://localhost:8081/auth/register", {
        username: data.username,
        email: data.email,
        password: data.password,
        role: data.role, // Optional, depending on your registration model
      });

      // If registration is successful
      alert("✅ Registration Successful!");
      navigate("/login"); // Redirect to login page after successful registration
    } catch (error) {
      // If an error occurs during registration (e.g., username already taken)
      if (error.response && error.response.data) {
        setRegisterError(error.response.data); // Display the error message
      } else {
        setRegisterError("Something went wrong. Please try again.");
      }
    }
  };

  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
        <h2>Create Account</h2>

        {/* Username */}
        <label>Username</label>
        <input
          type="text"
          {...register("username", { required: "Username is required" })}
          placeholder="Enter your username"
        />
        {errors.username && <p className="error">{errors.username.message}</p>}

        {/* Role */}
        <label>Role</label>
        <select {...register("role", { required: "Role is required" })}>
          <option value="">-- Select Role --</option>
          <option value="BUYER">Buyer</option>
          <option value="SELLER">Seller</option>
          <option value="ADMIN">Admin</option>
        </select>
        {errors.role && <p className="error">{errors.role.message}</p>}

        {/* Email */}
        <label>Email</label>
        <input
          type="email"
          {...register("email", {
            required: "Email is required",
            pattern: { value: /\S+@\S+\.\S+/, message: "Invalid email" },
          })}
          placeholder="Enter your email"
        />
        {errors.email && <p className="error">{errors.email.message}</p>}

        {/* Password */}
        <label>Password</label>
        <input
          type="password"
          {...register("password", {
            required: "Password is required",
            minLength: { value: 6, message: "Min length is 6" },
            pattern: {
              value: /^(?=.*[!@#$%^&*(),.?":{}|<>])/,
              message: "Password must contain at least one special character",
            },
          })}
          placeholder="Enter your password"
        />
        {errors.password && <p className="error">{errors.password.message}</p>}

        {/* Display error message */}
        {registerError && <p className="error">{registerError}</p>}

        <button type="submit">Register</button>

        <p className="login-link">
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </form>
    </div>
  );
}


import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/users/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });
      const result = await response.json();

      if (response.ok) {
        console.log("User data:", result.data);
        console.log("name", result.data.name);
        console.log("id", result.data.id);

        localStorage.setItem("userId", result.data.id);
        localStorage.setItem("userName", result.data.name);
        alert("Login successful!");
        navigate("/"); // Redirect to the homepage or dashboard
      } else {
        setErrorMessage(result.message || "Login failed");
      }
    } catch (error) {
      setErrorMessage("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Welcome Back</h2>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <form onSubmit={handleLogin}>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            placeholder="Enter your email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            placeholder="Enter your password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button type="submit" className="login-button">Login</button>
        </form>
        <p className="signup-message">
          Don't have an account?{" "}
          <a href="/signup" className="signup-link">
            Sign Up
          </a>
        </p>
      </div>
    </div>
  );
};

export default Login;

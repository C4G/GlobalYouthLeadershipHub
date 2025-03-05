import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styles from "../styles/LoginPage.module.css";

const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [maskedValue, setMaskedValue] = useState("");
  const [error, setError] = useState("");
  const [reset, setReset] = useState(false);

  // Reset email and password if navigating from logout
  useEffect(() => {
    if (location.state?.reset) {
      setEmail("");
      setPassword("");
      setError("");
      setReset(false);
    }
  }, [location.state]);

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    setError("");
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
    setMaskedValue("*".repeat(e.target.value.length));
    setError("");
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (email === "admin@test.com" && password === "admin") {
      navigate("/login-success", { replace: true, state: { email, password, reset } });
    } else if (email === "admin@test.com" && password !== "admin") {
      setError("Incorrect password. Please enter it again.");
    } else {
      setError("User does not exist! Please register an account.");
    }
  };

  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International"
          className={styles.organizationLogo}
          loading="lazy"
        />
        <p className={`${styles.description} ${styles.hiddenOnMobile}`}>
          The On-Demand Youth Leadership Program is an initiative of the U.S.
          Department of State’s Bureau of Educational and Cultural Affairs (ECA)
          administered by Legacy International
        </p>
      </div>

      {/* Form Section */}
      <div className={styles.rightSection}>
        <div className={styles.formContainer}>
          <form className={styles.loginForm} onSubmit={handleSubmit}>
            <div className={styles.inputGroup}>
              <label htmlFor="email">Email address</label>
              <input
                type="email"
                id="email"
                placeholder="Enter your email address"
                value={email}
                onChange={handleEmailChange}
                aria-required="true"
                required
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                placeholder="********"
                value={password}
                onChange={handlePasswordChange}
                aria-required="true"
                required
              />
            </div>
            {error && <p className={styles.errorMessage}>{error}</p>}
            <button type="submit" className={styles.signInBtn}>
              SIGN IN
            </button>
          </form>
          <hr />
          <button
            type="button"
            className={styles.createAccountBtn}
            onClick={() => navigate("/signup")}
          >
            REGISTER ACCOUNT
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;

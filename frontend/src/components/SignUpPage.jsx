import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "../styles/SignUpPage.module.css";

const SignUpPage = () => {
  const navigate = useNavigate();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [day, setDay] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [email, setEmail] = useState("");
  const [emailExistMessage, setEmailExistMessage] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleFirstName = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastName = (e) => {
    setLastName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    if (e.target.value === "admin@test.com") {
      setEmailExistMessage("This user already exists!");
    } else {
      setEmailExistMessage("");
    }
  };

  const handleDayChange = (e) => {
    setDay(e.target.value);
  };
  const handleMonthChange = (e) => {
    setMonth(e.target.value);
  };
  const handleYearChange = (e) => {
    setYear(e.target.value);
  };

  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);

    if (newPassword.length < 8) {
      setErrorMessage("Password must be at least 8 characters long");
    } else if (confirmPassword && newPassword !== confirmPassword) {
      setErrorMessage("Passwords do not match");
    } else {
      setErrorMessage("");
    }
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
    if (password && e.target.value !== password) {
      setErrorMessage("Passwords do not match");
    } else {
      setErrorMessage("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (emailExistMessage || errorMessage) {
      return;
    }

    try {
      const userData = {
        email,
        password,
        firstName,
        lastName,
        dateOfBirth: `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`,
      };

      // await registerUser(userData);
      // Navigate to success page only if passwords match
      navigate("/signup-success", { state: { email } });
    } catch (error) {
      setErrorMessage(
        error.message || "Registration failed. Please try again."
      );
    }
  };


  const registerUser = async (userData) => {
    try {
      const response = await fetch("/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        throw new Error("Registration failed");
      }

      return await response.json();
    } catch (error) {
      console.error("Error:", error);
      throw error;
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
          <h2>Register a new account</h2>
          <p>Signing up for an account is subject to admin's approval</p>
          <hr />

          <form className={styles.registerForm} onSubmit={handleSubmit}>
            {/* Name Fields */}
            <div className={styles.nameGroup}>
              <div className={styles.inputGroup}>
                <input
                  type="text"
                  id="first-name"
                  name="first-name"
                  placeholder="First name *"
                  aria-required="true"
                  value={firstName}
                  onChange={handleFirstName}
                  required
                />
              </div>
              <div className={styles.inputGroup}>
                <input
                  type="text"
                  id="last-name"
                  name="last-name"
                  placeholder="Last name *"
                  aria-required="true"
                  value={lastName}
                  onChange={handleLastName}
                  required
                />
              </div>
            </div>

            {/* Date of Birth */}
            <div className={styles.dobGroup}>
              <label className={styles.dobGlabel}>Date of Birth</label>
              <div className={styles.dobFields}>
                <select
                  name="day"
                  aria-label="Day"
                  value={day}
                  onChange={handleDayChange}
                  required
                >
                  <option value="">Day</option>
                  {[...Array(31)].map((_, i) => (
                    <option key={i + 1} value={i + 1}>
                      {i + 1}
                    </option>
                  ))}
                </select>
                <select
                  name="month"
                  aria-label="Month"
                  value={month}
                  onChange={handleMonthChange}
                  required
                >
                  <option value="">Month</option>
                  {[
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "Jun",
                    "Jul",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec",
                  ].map((month, i) => (
                    <option key={i + 1} value={i + 1}>
                      {month}
                    </option>
                  ))}
                </select>
                <select
                  name="year"
                  aria-label="Year"
                  value={year}
                  onChange={handleYearChange}
                  required
                >
                  <option value="">Year</option>
                  {[...Array(40)].map((_, i) => (
                    <option key={2025 - i} value={2025 - i}>
                      {2025 - i}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            {/* Email & Password */}
            <div className={styles.emailAddress}>
              <input
                type="email"
                id="email"
                name="email"
                placeholder="Email Address"
                aria-required="true"
                value={email}
                onChange={handleEmailChange}
                required
              />
            </div>

            {/* Error Message */}
            {emailExistMessage && (
              <p className={styles.errorMessage}>{emailExistMessage}</p>
            )}

            <div className={styles.newPassword}>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="New Password"
                value={password}
                onChange={handlePasswordChange}
                aria-required="true"
                required
              />
            </div>
            <div className={styles.confirmPassword}>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
                aria-required="true"
                required
              />
            </div>

            {/* Error Message */}
            {errorMessage && (
              <p className={styles.errorMessage}>{errorMessage}</p>
            )}

            {/* Submit Button */}
            <button type="submit" className={styles.createAccountBtn}>
              REGISTER ACCOUNT
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;

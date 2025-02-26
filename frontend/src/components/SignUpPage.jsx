import React from "react";
import styles from "../styles/SignUpPage.module.css";

const SignUpPage = () => {
  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International"
          className={styles.organizationLogo}
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

          <form className={styles.registerForm}>
            {/* Name Fields */}
            <div className={styles.nameGroup}>
              <div className={styles.inputGroup}>
                <input
                  type="text"
                  id="first-name"
                  name="first-name"
                  placeholder="First name *"
                  aria-required="true"
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
                  required
                />
              </div>
            </div>

            {/* Date of Birth */}
            <div className={styles.dobGroup}>
              <label className={styles.dobGlabel}>Date of Birth</label>
              <div className={styles.dobFields}>
                <select name="day" aria-label="Day">
                  <option value="">Day</option>
                  {[...Array(31)].map((_, i) => (
                    <option key={i + 1} value={i + 1}>
                      {i + 1}
                    </option>
                  ))}
                </select>
                <select name="month" aria-label="Month">
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
                <select name="year" aria-label="Year">
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
                required
              />
            </div>
            <div className={styles.newPassword}>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="New Password"
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
                aria-required="true"
                required
              />
            </div>

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

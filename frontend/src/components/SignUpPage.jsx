import React from "react";
import styles from "../styles/SignUpPage.module.css";

const SignUpPage = () => {
  return (
    <div className={styles.signupContainer}>
      {/* Logo Section */}
      <div className={styles.logoSection}>
        <img src="/organizationLogo.jpg" alt="Legacy International Logo" className={styles.organizationLogo} />
                <p className={`${styles.description} ${styles.hiddenOnMobile}`}>
                  An initiative of the U.S. Department of State's Bureau of Educational
                  and Cultural Affairs (ECA) administered by Legacy International
                </p>
        <img src="/programLogo.jpg" alt="Youth Leadership Logo" className={styles.mobileLogo} />
      </div>

      {/* Form Section */}
      <div className={styles.formSection}>
        <h2>Create a new account</h2>
        <p>Signing up for an account is subject to admin's approval.</p>

        <form>
          {/* Name Fields */}
          <div className={styles.nameGroup}>
          <div className={styles.inputGroup}>
            <label htmlFor="first-name"></label>
            <input type="text" id="first-name" name="first-name" placeholder="First name *" aria-required="true" />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="last-name"></label>
            <input type="text" id="last-name" name="last-name" placeholder="Last name *" aria-required="true" />
          </div>
          </div>

          {/* Date of Birth */}
          <div className={styles.dobGroup}>
            <label className={styles.dobGlabel}>Date of Birth</label>
            <div className={styles.dobFields}>
              <select name="day" aria-label="Day">
                <option value="">Day</option>
                {[...Array(31)].map((_, i) => (
                  <option key={i + 1} value={i + 1}>{i + 1}</option>
                ))}
              </select>
              <select name="month" aria-label="Month">
                <option value="">Month</option>
                {["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].map((month, i) => (
                  <option key={i + 1} value={i + 1}>{month}</option>
                ))}
              </select>
              <select name="year" aria-label="Year">
                <option value="">Year</option>
                {[...Array(40)].map((_, i) => (
                  <option key={2025 - i} value={2025 - i}>{2025 - i}</option>
                ))}
              </select>
            </div>
          </div>

          {/* Gender */}
          <fieldset className={styles.genderGroup} aria-labelledby="gender-label">
            <legend id="gender-label" className={styles.genderLegend}>Gender</legend>
            <label className={styles.genderOption}>
              <input type="radio" name="gender" value="female" defaultChecked aria-labelledby="gender-female" />
              <span id="gender-female">Female</span>
            </label>
            <label className={styles.genderOption}>
              <input type="radio" name="gender" value="male" aria-labelledby="gender-male" />
              <span id="gender-male">Male</span>
            </label>
          </fieldset>

          {/* Email & Password */}
          <div className={styles.emailGroup}>
            <label htmlFor="email"></label>
            <input type="email" id="email" name="email" placeholder="Email Address" aria-required="true"/>
          </div>
          <div className={styles.passwordGroup}>
            <label htmlFor="password"></label>
            <input type="password" id="password" name="password" placeholder="New Password" aria-required="true" />
          </div>

          {/* Submit Button */}
          <button type="submit" className={styles.createAccountBtn}>CREATE ACCOUNT</button>
        </form>
      </div>
    </div>
  );
};

export default SignUpPage;

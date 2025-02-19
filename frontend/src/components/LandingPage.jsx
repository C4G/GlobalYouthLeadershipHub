import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "../styles/LandingPage.module.css";

const LandingPage = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div className={styles.leftSectio}>
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International Logo"
          className={styles.organizationLogo}
        />
        <p className={`${styles.description} ${styles.hiddenOnMobile}`}>
          An initiative of the U.S. Department of State's Bureau of Educational
          and Cultural Affairs (ECA) administered by Legacy International
        </p>
        <img
          src="/programLogo.jpg"
          alt="Youth Leadership Logo"
          className={styles.programLogo}
        />
      </div>

      <div className={styles.rightSection}>
        <div className={styles.formContainer}>
          <form>
            <div className={styles.inputGroup}>
              <label htmlFor="email">Email address</label>
              <input
                type="email"
                id="email"
                placeholder="Enter your email address"
                aria-required="true"
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                placeholder="********"
                aria-required="true"
              />
            </div>
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
            CREATE ACCOUNT
          </button>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;

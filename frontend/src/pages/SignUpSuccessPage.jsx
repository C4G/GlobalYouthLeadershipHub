import { useLocation } from "react-router-dom";
import styles from "@/styles/SignUpSuccessPage.module.css";
import Logo from "@/components/Logo";

const SignUpSuccessPage = () => {
  const location = useLocation();
  const email = location.state?.email || "your email"; // Default fallback

  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
<<<<<<< HEAD:frontend/src/components/SignUpSuccessPage.jsx
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International"
          className={styles.organizationLogo}
          loading="lazy"
        />
        <p className={`${styles.description} ${styles.hiddenOnMobile}`}>
          The On-Demand Youth Leadership Program is an initiative of the U.S.
          Department of State&apos;s Bureau of Educational and Cultural Affairs (ECA)
          administered by Legacy International
        </p>
=======
        <Logo />
>>>>>>> ac64343 (Refactor from page to components):frontend/src/pages/SignUpSuccessPage.jsx
      </div>

      {/* Message Section */}
      <div className={styles.rightSection}>
        <div className={styles.messageContainer}>
          <h2 className={styles.successTitle}>Successful Registration</h2>
          <p className={styles.successMessage}>
            Account for <strong>{email}</strong> has been registered
            successfully.
          </p>
          <p className={styles.notice}>
            Account creation is subjected to admin&apos;s approval.
          </p>
        </div>
      </div>
    </div>
  );
};

export default SignUpSuccessPage;

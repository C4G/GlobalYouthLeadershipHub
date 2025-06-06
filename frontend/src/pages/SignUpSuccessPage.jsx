import { useLocation, useNavigate } from "react-router-dom";
import Logo from "@/components/Logo";
import styles from "./SignUpSuccessPage.module.css";

const SignUpSuccessPage = () => {
  const location = useLocation();
  const navigate = useNavigate()
  const email = location.state?.email || "your email"; // Default fallback


  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
        <Logo />
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
          <button onClick={() => navigate('/')} className={styles.returnToLoginBtn}>
            Return to Login Page
          </button>
        </div>
      </div>
    </div>
  );
};

export default SignUpSuccessPage;

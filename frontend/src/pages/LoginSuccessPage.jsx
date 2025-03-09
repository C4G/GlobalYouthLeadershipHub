import { useNavigate, useLocation } from "react-router-dom";
import styles from "@/styles/LoginSuccessPage.module.css";
import Logo from "@/components/Logo";

const LoginSuccessPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email || "your email";

  console.log(location.state.password);

  const handleLogout = () => {
    navigate("/", { replace: true, reset: true });
  };

  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
<<<<<<< HEAD:frontend/src/components/LoginSuccessPage.jsx
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
=======
        <Logo />
>>>>>>> ac64343 (Refactor from page to components):frontend/src/pages/LoginSuccessPage.jsx
      </div>

      {/* Message Section */}
      <div className={styles.rightSection}>
        <div className={styles.messageContainer}>
          <h2 className={styles.successTitle}>Successful Login</h2>
          <p className={styles.successMessage}>
            Welcome, <strong>{email}</strong>!
          </p>
          <button
            type="button"
            className={styles.signOutBtn}
            onClick={handleLogout}
          >
            SIGN OUT
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginSuccessPage;

import { useNavigate, useLocation } from "react-router-dom";
import styles from "@/styles/LoginSuccessPage.module.css";
import Logo from "@/components/Logo";

// TODO - to remove once landing page is up
const LoginSuccessPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email || "your email";

  console.log(location.state.password);

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate("/", { replace: true, reset: true });
  };

  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
        <Logo />
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

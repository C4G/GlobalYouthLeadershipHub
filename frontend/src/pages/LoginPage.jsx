import { useNavigate } from "react-router-dom";
import styles from "@/styles/pages/LoginPage.module.css";
import Logo from "@/components/Logo";
import LoginForm from "@/components/LoginForm";

const LoginPage = () => {
  const navigate = useNavigate();

  return (
    <div>
      <div className={styles.container}>
        {/* Logo Section */}
        <div className={styles.leftSection}>
          <Logo />
        </div>

        {/* Form Section */}
        <div className={styles.rightSection}>
          <div className={styles.formContainer}>
            <LoginForm />
            <button
              type="button"
              className={styles.forgotPasswordBtn}
              onClick={() => navigate('/forgot-password')}
            >
              Forgotten Password?
            </button>
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
      {/* For the team taking over, you can put the team homepage here */}
      {/* <a href="/team-homepage-spr25" className={styles.homepageLink}>Team Homepage</a> */}
    </div>
  );
};

export default LoginPage;

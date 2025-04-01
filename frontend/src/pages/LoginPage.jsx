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
    <a href="/team-homepage-spr25" className={styles.homepageLink}>Team Homepage</a>
    <a href="https://drive.google.com/file/d/1GjxNukWve8xrKWZeyArVAr41asHU_kfX/view?usp=drive_link"
        className={styles.homepageLink}
    >
      P6-Demo
    </a>
    </div>
  );
};

export default LoginPage;

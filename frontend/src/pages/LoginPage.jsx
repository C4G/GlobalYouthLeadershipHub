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
    <a href="https://gtvault-my.sharepoint.com/:v:/g/personal/mrysavy3_gatech_edu/EXvn5gfyoZlOjmqIRuN9fEkBkf5DzySRUwzRGpeu7-CGXA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=At0cSM"
        className={styles.homepageLink}
    >
      P6-Demo
    </a>
    </div>
  );
};

export default LoginPage;

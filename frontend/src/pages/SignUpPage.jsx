import Logo from "@/components/Logo";
import SignUpForm from "@/components/SignUpForm";
import styles from "@/styles/SignUpPage.module.css";

const SignUpPage = () => {
  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
<<<<<<< HEAD:frontend/src/components/SignUpPage.jsx
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
>>>>>>> ac64343 (Refactor from page to components):frontend/src/pages/SignUpPage.jsx
      </div>

      {/* Form Section */}
      <div className={styles.rightSection}>
        <div className={styles.formContainer}>
          <h2>Register a new account</h2>
          <p>Signing up for an account is subject to admin&apos;s approval</p>
          <hr />
          <SignUpForm />
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;

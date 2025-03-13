import Logo from "@/components/Logo";
import SignUpForm from "@/components/SignUpForm";
import styles from "@/styles/SignUpPage.module.css";

const SignUpPage = () => {
  return (
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
        <Logo />
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

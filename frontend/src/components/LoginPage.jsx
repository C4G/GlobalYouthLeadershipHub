import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styles from "@/styles/LoginPage.module.css";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";

const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const mutation = useMutation({
    mutationFn: async ({ email, password }) => {
      return await customFetcher("/auth/login", "POST", null, { email, password })
    },
    onSuccess: (data) => {
      if (data?.token) {
        localStorage.setItem("jwtToken", data.token)
        navigate("/login-success", { replace: true, state: { email } });
      } else {
        setError("Invalid response from server")
      }
    },
    onError: (error) => {
      setError(error.message || "Login failed")
    }
  })

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    setError("");
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
    setError("");
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("")
    mutation.mutate({ email, password })
  };

  // Reset email and password if navigating from logout
  useEffect(() => {
    if (location.state?.reset) {
      setEmail("");
      setPassword("");
      setError("");
    }
  }, [location.state]);

  return (
    <div>
    <div className={styles.container}>
      {/* Logo Section */}
      <div className={styles.leftSection}>
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
      </div>

      {/* Form Section */}
      <div className={styles.rightSection}>
        <div className={styles.formContainer}>
          <form className={styles.loginForm} onSubmit={handleSubmit}>
            <div className={styles.inputGroup}>
              <label htmlFor="email">Email address</label>
              <input
                type="email"
                id="email"
                placeholder="Enter your email address"
                value={email}
                onChange={handleEmailChange}
                aria-required="true"
                required
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                placeholder="********"
                value={password}
                onChange={handlePasswordChange}
                aria-required="true"
                required
              />
            </div>
            {error && <p className={styles.errorMessage}>{error}</p>}
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
            REGISTER ACCOUNT
          </button>
        </div>
      </div>
    </div>
    <a href="/team-homepage" className={styles.homepageLink}>Team Homepage</a>
    </div>
  );
};

export default LoginPage;

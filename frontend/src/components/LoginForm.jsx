import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";
import styles from "@/styles/components/LoginForm.module.css"
import { useSetJWTToken } from "@/hooks/auth";

const LoginForm = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const { mutate: setJWTToken } = useSetJWTToken()

    const mutation = useMutation({
        mutationFn: async (loginCredentials) => {
            return await customFetcher("/auth/login", "POST", null, loginCredentials)
        },
        onSuccess: (data) => {
            if (data) {
                setJWTToken(data)
                navigate("/landing", { replace: true, state: { user: email } });
            } else {
                setError("Invalid response from server")
            }
        },
        onError: (error) => {
            setError(error.message || "Login failed")
        }
    })

    const handleSubmit = (e) => {
        e.preventDefault();
        setError("")
        const loginCredentials = { email, password }
        mutation.mutate(loginCredentials)
    };


    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setError("");
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        setError("");
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
        <>
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
        </>
    )
}

export default LoginForm
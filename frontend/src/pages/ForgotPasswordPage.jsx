import { useNavigate } from "react-router-dom"
import ForgotPasswordForm from "@/components/ForgotPasswordForm"
import Logo from "@/components/Logo"

import styles from "./ForgotPasswordPage.module.css"

const ForgotPasswordPage = () => {
    const navigate = useNavigate()

    return (
        <div className={styles.container}>
            {/* Logo Section */}
            <div className={styles.leftSection}>
                <Logo />
            </div>

            {/* Forgot Password Section */}
            <div className={styles.rightSection}>
                <div className={styles.forgotPasswordFormContainer}>
                    <h2>Reset Password</h2>
                    <ForgotPasswordForm />
                    <hr />
                    <button onClick={() => navigate('/')} className={styles.returnToLoginBtn}>
                        Return to Login Page
                    </button>
                </div>
            </div>
        </div>
    )
}

export default ForgotPasswordPage
/* eslint-disable react/prop-types */
import styles from "@/styles/components/ForgotPasswordForm.module.css"
import { useState } from "react";

const Modal = ({ children }) => {
    return (
        <div className={styles.backdrop}>
            <div className={styles.modal}>
                {children}
            </div>
        </div>
    )
}

const ForgotPasswordForm = () => {
    const [email, setEmail] = useState("");
    const [error, setError] = useState("");

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setError("");
    };

    const onHandleResetConfirmation = (event) => {
        event.preventDefault()
        setShowModal(true)
    }

    const [showModal, setShowModal] = useState(false)

    // TODO - to integrate with API
    const handleConfirmReset = () => {
        setShowModal(false)
    }

    // TODO - to integrate with API
    const handleCancelReset = () => {
        setShowModal(false)
    }

    return (
        <>
            <form className={styles.resetForm} onSubmit={onHandleResetConfirmation}>
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
                {error && <p className={styles.errorMessage}>{error}</p>}
                <button type="submit" className={styles.resetBtn}>
                    Reset Password
                </button>
            </form>

            {showModal &&
                <Modal>
                    <div className={styles.modalContent}>
                        <p>Are you sure you want to reset your password?</p>
                        <div className={styles.modalActions}>
                            <button onClick={handleConfirmReset} className={styles.confirmBtn}>
                                Yes, Reset
                            </button>
                            <button onClick={handleCancelReset} className={styles.cancelBtn}>
                                Cancel
                            </button>
                        </div>
                    </div>
                </Modal>
            }
        </>
    )
}

export default ForgotPasswordForm
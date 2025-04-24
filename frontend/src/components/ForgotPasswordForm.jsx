/* eslint-disable react/prop-types */
import customFetcher from "@/services/api";
import styles from "@/styles/components/ForgotPasswordForm.module.css"
import { useMutation } from "@tanstack/react-query";
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

    const { mutate, isPending, } = useMutation({
        mutationFn: async (resetEmail) => {
            return await customFetcher("/auth/request-password-reset", "POST", null, resetEmail)
        },
        onSuccess: (data) => {
            setEmail("")
            setError("")
            setShowModal(false)
            alert(`${data.message}\nAdmin will contact you once your password has been reset`)
        },
        onError: (error) => {
            setShowModal(false)
            setError(error.message || "Login failed")
        }
    })

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setError("");
    };

    const onHandleResetConfirmation = (event) => {
        event.preventDefault()
        setShowModal(true)
    }

    const [showModal, setShowModal] = useState(false)

    const handleConfirmReset = () => {
        const resetEmail = { email }
        mutate(resetEmail)
    }

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
                <button
                    type="submit"
                    className={styles.resetBtn}
                    disabled={isPending}
                >
                    {isPending ? "Sending" : "Reset Password"}
                </button>
            </form>

            {showModal &&
                <Modal>
                    <div className={styles.modalContent}>
                        <p>Are you sure you want to reset your password?</p>
                        <div className={styles.modalActions}>
                            <button onClick={handleConfirmReset} className={styles.confirmBtn}>
                                {isPending ? "Processing..." : "Yes, Reset"}
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
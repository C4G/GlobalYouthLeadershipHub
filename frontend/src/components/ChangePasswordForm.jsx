import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";

import styles from "@/styles/components/ChangePasswordForm.module.css"

const ChangePasswordForm = () => {
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");
    const [error, setError] = useState("")

    const { mutate, isPending } = useMutation({
        mutationFn: (changedPassword) => customFetcher("/user/reset-password", "POST", null, changedPassword),
        onSuccess: (data) => {
            setCurrentPassword("")
            setNewPassword("")
            setConfirmNewPassword("")
            alert(data.message)
        },
        onError: (error) => setError(error.message || "Failed to change password")
    })

    const handleChangeCurrentPassword = (e) => {
        setCurrentPassword(e.target.value);
    }

    const handleChangeNewPassword = (e) => {
        const value = e.target.value;
        setNewPassword(value);

        if (value.length < 8) {
            setError("Password must be at least 8 characters long");
        } else if (confirmNewPassword && confirmNewPassword !== newPassword) {
            setError("Passwords do not match");
        } else {
            setError("");
        }
    }

    const handleChangeConfirmedNewPassword = (e) => {
        const value = e.target.value;
        setConfirmNewPassword(value);

        if (newPassword && newPassword !== value) {
            setError("Passwords do not match");
        } else {
            setError("");
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault()

        if (newPassword.length < 8) {
            setError("Password must be at least 8 characters long");
            return;
        }

        if (newPassword !== confirmNewPassword) {
            setError("Password do not match!");
            return;
        }

        setError("")

        const changedPassword = {
            "currentPassword": currentPassword,
            "newPassword": confirmNewPassword
        }

        mutate(changedPassword)
    }

    return (

        <form onSubmit={handleSubmit} className={styles.changedPasswordForm}>
            <div className={styles.inputGroup}>
                <label htmlFor="currentPassword">Current Password</label>
                <input
                    type="password"
                    id="currentPassword"
                    placeholder="********"
                    value={currentPassword}
                    onChange={handleChangeCurrentPassword}
                    required
                />
            </div>
            <div className={styles.inputGroup}>
                <label htmlFor="newPassword">New Password</label>
                <input
                    type="password"
                    id="newPassword"
                    placeholder="********"
                    value={newPassword}
                    onChange={handleChangeNewPassword}
                    required
                />
            </div>
            <div className={styles.inputGroup}>
                <label htmlFor="confirmNewPassword">Confirm New Password</label>
                <input
                    type="password"
                    id="confirmNewPassword"
                    placeholder="********"
                    value={confirmNewPassword}
                    onChange={handleChangeConfirmedNewPassword}
                    required
                />
            </div>
            {error && <p className={styles.errorMessage}>{error}</p>}
            <button type="submit" disabled={isPending} className={styles.changePasswordBtn}>
                {isPending ? "Updating..." : "Change Password"}
            </button>
        </form>
    )
}

export default ChangePasswordForm
import { useState } from "react";
import { useGetListOfResetUsers } from "@/hooks/users"
import Spinner from "@/components/Spinner"
import customFetcher from "@/services/api";
import { useMutation } from "@tanstack/react-query";

import styles from "./ResetPassword.module.css"

const ResetPassword = () => {
    const [error, setError] = useState("");
    const [resettingUser, setResettingUser] = useState(null);

    const { data: resetUsers = [], isLoading, refetch } = useGetListOfResetUsers()

    const { mutate } = useMutation({
        mutationFn: (email) => customFetcher("/admin/reset-users-password", "POST", null, { email }),
        onMutate: (email) => setResettingUser(email),
        onSuccess: (data) => {
            alert(`${data.message}\nDefault Password will be Last Name + MMDDYYYY in lowercase (e.g. doe12312000)`);
            setResettingUser(null);
            refetch()
        },
        onError: (err) => {
            setError(err.message || "Failed to reset password");
            setResettingUser(null);
        }
    });

    const handleReset = (email) => {
        mutate(email);
    };

    if (isLoading) return (
        <div className={styles.resetPasswordSpinner}>
            <Spinner text={"Fetching all users that requested password reset..."} />
        </div>
    )

    return (
        <>
            <div className={styles.header}>
                <h1>Reset Password Panel</h1>
            </div>
            <div className={styles.dashboard}>
                {resetUsers.length === 0 && <h3>No users have requested a reset</h3>}
                {error && <p className={styles.error}>{error}</p>}
                <ul className={styles.userList}>
                    {resetUsers.map((user) => (
                        <li key={user.email} className={styles.userItem}>
                            <div className={styles.userInfo}>
                                <label>
                                    <span>{user.email}</span>
                                </label>
                            </div>
                            <button
                                onClick={() => handleReset(user.email)}
                                disabled={resettingUser === user.email}
                            >
                                {resettingUser === user.email ? "Resetting..." : "Reset"}
                            </button>
                        </li>
                    ))
                    }
                </ul>
            </div>
        </>
    )
}

export default ResetPassword
import { useState } from "react";
import { useGetListOfResetUsers } from "@/hooks/users"
import Spinner from "@/components/Spinner"

import styles from "@/styles/components/ResetPassword.module.css"
import customFetcher from "@/services/api";
import { useMutation } from "@tanstack/react-query";

const ResetPassword = () => {
    const [selectedUsers, setSelectedUsers] = useState(new Set());
    const [error, setError] = useState("");
    const [resettingUser, setResettingUser] = useState(null);


    const { data: resetUsers = [], isLoading, refetch } = useGetListOfResetUsers()

    const { mutate } = useMutation({
        mutationFn: (email) => customFetcher("/admin/reset-users-password", "POST", null, { email }),
        onMutate: (email) => setResettingUser(email),
        onSuccess: (_, email) => {
            alert(`Password for ${email} has been reset to default`);

            setSelectedUsers((prev) => {
                const next = new Set(prev);
                next.delete(email);
                return next;
            });

            setResettingUser(null);
            refetch()
        },
        onError: (err) => {
            setError(err.message || "Failed to reset password");
            setResettingUser(null);
        }
    });

    const handleCheckboxToggle = (email) => {
        setSelectedUsers((prev) => {
            const next = new Set(prev);
            next.has(email) ? next.delete(email) : next.add(email);
            return next;
        });
    };

    const handleReset = (email) => {
        mutate(email);
    };

    if (isLoading) return <Spinner text={"Fetching all resets users..."} />

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
                                    <input
                                        type="checkbox"
                                        checked={selectedUsers.has(user.email)}
                                        onChange={() => handleCheckboxToggle(user.email)}
                                    />
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
import { useState } from "react";
import queryClient from "@/libs/queryClient";
import PendingUsersList from "@/components/PendingUsersList";
import SelectedPendingUser from "@/components/SelectedPendingUser";

import styles from "./UserVerification.module.css"

const UserVerification = () => {
    const [selectedUser, setSelectedUser] = useState(null)

    const refetchUnverifiedUsers = () => {
        queryClient.invalidateQueries(["unverifiedUsers"])
        setSelectedUser(null)
    }

    const handleSelect = (user) => {
        setSelectedUser(user)
    }

    return (
        <>
            <div className={styles.header}>
                <h1>User Verification Panel</h1>
            </div>
            <div className={styles.dashboard}>
                <div className={styles.card}>
                    <PendingUsersList onSelect={handleSelect} />
                </div>
                <div className={styles.card} >
                    <SelectedPendingUser user={selectedUser} refetchUnverifiedUsers={refetchUnverifiedUsers} />
                </div>
            </div>
        </>
    )
}

export default UserVerification
import Container from "@/components/Container";
import PendingUsersList from "@/components/PendingUsersList";
import SelectedPendingUser from "@/components/SelectedPendingUser";
import Sidebar from "@/components/Sidebar"
import queryClient from "@/libs/queryClient";
import styles from "@/styles/pages/AdminPortalPage.module.css";

import { useState } from "react";

const AdminPortalPage = () => {
    const [selectedUser, setSelectedUser] = useState(null)

    const refetchUnverifiedUsers = () => {
        queryClient.invalidateQueries(["unverifiedUsers"])
        setSelectedUser(null)
    }

    const handleSelect = (user) => {
        setSelectedUser(user)
    }

    return (
        <Container>
            <Sidebar />
            <main className={styles.mainContent}>
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
            </main >
        </Container>
    )
}

export default AdminPortalPage
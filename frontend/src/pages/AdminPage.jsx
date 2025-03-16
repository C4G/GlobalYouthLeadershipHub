import Sidebar from "@/components/Sidebar"
import { useGetUnverifiedUsers } from "@/hooks/users";
import styles from "@/styles/AdminPage.module.css";

const AdminPage = () => {

    const { data: unverifiedUsers } = useGetUnverifiedUsers()
    console.log('unverifiedUsers', unverifiedUsers)

    return (
        <div className={styles.container}>
            <Sidebar />
            <main className={styles.mainContent}>
                <div className={styles.header}>
                    <h1>User Verification Panel</h1>
                </div>
                <div className={styles.dashboard}>
                    <div className={styles.card}>
                        <h2>Pending Users</h2>
                        <div className={styles.userList}>
                            <ul>
                                <li>User 1 - user1@email.com</li>
                                <li>User 2 - user2@email.com</li>
                                <li>User 3 - user3@email.com</li>
                                <li>User 4 - user4@email.com</li>
                                <li>User 1 - user1@email.com</li>
                                <li>User 2 - user2@email.com</li>
                                <li>User 3 - user3@email.com</li>
                                <li>User 4 - user4@email.com</li>
                                <li>User 1 - user1@email.com</li>
                                <li>User 2 - user2@email.com</li>
                                <li>User 3 - user3@email.com</li>
                                <li>User 4 - user4@email.com</li>
                                <li>User 1 - user1@email.com</li>
                                <li>User 2 - user2@email.com</li>
                                <li>User 3 - user3@email.com</li>
                                <li>User 4 - user4@email.com</li>
                            </ul>
                        </div>

                    </div>

                    <div className={styles.card} >
                        <h2>Selected Pending User Profile</h2>
                        <div className={styles.verifySection}>
                            <p><strong>Name:</strong> John Doe</p>
                            <p><strong>Email:</strong> johndoe@email.com</p>
                            <p><strong>Status:</strong> Pending Verification</p>
                            <button>Verify User</button>
                        </div>
                    </div>
                </div>
            </main >
        </div >
    )
}

export default AdminPage
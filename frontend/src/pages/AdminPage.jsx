import Sidebar from "@/components/Sidebar"
import styles from "@/styles/AdminPage.module.css";

const AdminPage = () => {
    return (
        <div className={styles.container}>
            <Sidebar />
            <main className={styles.mainContent}>
                <h1>
                    This is admin page
                </h1>
            </main>
        </div>
    )
}

export default AdminPage
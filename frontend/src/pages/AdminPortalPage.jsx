import Container from "@/components/Container";
import ResetPassword from "@/components/ResetPassword";
import Sidebar from "@/components/Sidebar"
import TabSwitcher from "@/components/TabSwitcher";
import UserVerification from "@/components/UserVerification";

import styles from "@/styles/pages/AdminPortalPage.module.css";

const AdminPortalPage = () => {
    const TABS_HEADER = ["User Verification", "Reset Password"]


    return (
        <Container>
            <Sidebar />
            <main className={styles.mainContent}>
                <TabSwitcher
                    tabs={TABS_HEADER}
                    renderContent={(tab) => {
                        if (tab === TABS_HEADER[0]) return <UserVerification />
                        if (tab === TABS_HEADER[1]) return <ResetPassword />
                    }}
                />
            </main >
        </Container>
    )
}

export default AdminPortalPage
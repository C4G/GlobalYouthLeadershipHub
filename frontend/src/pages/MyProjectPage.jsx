import Container from "@/components/Container";
import Sidebar from "@/components/Sidebar";
import TabSwitcher from "@/components/TabSwitcher";
import MyProjects from "@/components/MyProjects";
import ChangePasswordForm from "@/components/ChangePasswordForm";

import styles from "@/styles/pages/MyProjectPage.module.css"

const MyProjectPage = () => {
    const TABS_HEADER = ["My Projects", "Change Password"]

    return (
        <Container>
            <Sidebar />
            <main className={styles.mainContent}>
                <TabSwitcher
                    tabs={TABS_HEADER}
                    renderContent={(tab) => {
                        if (tab === TABS_HEADER[0]) return <MyProjects />
                        if (tab === TABS_HEADER[1]) return <ChangePasswordForm />
                    }}
                />
            </main >
        </Container >
    );
}

export default MyProjectPage
/* eslint-disable react/prop-types */
import { useState } from "react";

import styles from "./TabSwitcher.module.css"

const TabSwitcher = ({ tabs, renderContent }) => {
    const [activeTab, setActiveTab] = useState(tabs[0]);

    return (
        <div className={styles.tabWrapper}>
            <div className={styles.tabHeader}>
                {tabs.map((tab) => (
                    <button
                        key={tab}
                        className={`${styles.tabButton} ${activeTab === tab ? styles.activeTab : ""}`}
                        onClick={() => setActiveTab(tab)}
                    >
                        {tab}
                    </button>))}
            </div>
            <div className={styles.tabContent}>
                {renderContent(activeTab)}
            </div>
        </div>
    )
}

export default TabSwitcher
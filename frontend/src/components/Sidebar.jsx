import React from "react";
import styles from "@/styles/LandingPage.module.css";
import Logo from "@/components/Logo";
import HomeIcon from "@/components/icons/HomeIcon";
import CreatePostIcon from "@/components/icons/CreatePostIcon";
import MyPageIcon from "@/components/icons/MyPageIcon";

const Sidebar = () => {
  return (
    <aside className={styles.sidebar}>
 <Logo />
      <nav>
        <ul className={styles.navList}>
          <li>
            <button className={styles.navListButton}>
              <HomeIcon /> Home
            </button>
          </li>
          <li>
            <button className={styles.navListButton}>
              <CreatePostIcon /> Create Post
            </button>
          </li>
          <li>
            <button className={styles.navListButton}>
              <MyPageIcon /> My Page
            </button>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;

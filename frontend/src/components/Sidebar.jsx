/* eslint-disable react/prop-types */
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import Logo from "@/components/Logo";
import NavIcon from "@/components/icons/NavIcon";
import CloseIcon from "@/components/icons/CloseIcon";
import HomeIcon from "@/components/icons/HomeIcon";
import CreateProjectIcon from "@/components/icons/CreateProjectIcon";
import MyPageIcon from "@/components/icons/MyPageIcon";
import LogoutIcon from "@/components/icons/LogoutIcon";
import { useJWTToken, useRemoveJWTToken } from "@/hooks/auth";
import AdminIcon from "@/components/icons/AdminIcon";

import styles from "./Sidebar.module.css";

const Sidebar = ({ setModalOpen, isLandingPage = false }) => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  // Check for admin access to render admin page component
  const { data: jwtToken } = useJWTToken();
  const adminAccess = jwtToken?.isAdmin;
  const { mutate: removeJWTToken } = useRemoveJWTToken();
  const handleLogout = () => {
    removeJWTToken();
    navigate("/", { replace: true });
  };

  return (
    <aside className={styles.sidebar}>
      <Logo />

      <button
        className={`${styles.hamburger} ${isOpen ? styles.open : ""}`}
        onClick={() => setIsOpen(!isOpen)}
      >
        {isOpen ? <CloseIcon /> : <NavIcon />}
      </button>

      <nav>
        <ul className={`${styles.navList} ${isOpen ? styles.open : ""}`}>

          <li>
            <button
              onClick={() => navigate("/landing")}
              className={styles.navListButton}
            >
              <HomeIcon /> Home
            </button>
          </li>

          {isLandingPage && (
            <li>
              <button
                onClick={() => {
                  setIsOpen(false);
                  setModalOpen(true);
                }}
                className={styles.navListButton}
              >
                <CreateProjectIcon /> Create Project
              </button>
            </li>
          )}

          <li>
            <button
              onClick={() => navigate("/project")}
              className={styles.navListButton}
            >
              <MyPageIcon /> My Page
            </button>
          </li>

          {adminAccess && (
            <li>
              <button
                onClick={() => navigate("/admin")}
                className={styles.navListButton}
              >
                <AdminIcon />
                Admin Portal
              </button>
            </li>
          )}

          <li>
            <button className={styles.navListButton} onClick={handleLogout}>
              <LogoutIcon /> Logout
            </button>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;

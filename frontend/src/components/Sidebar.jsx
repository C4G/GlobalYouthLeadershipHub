import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styles from "@/styles/components/Sidebar.module.css";
import Logo from "@/components/Logo";
import NavIcon from "@/components/icons/NavIcon";
import CloseIcon from "@/components/icons/CloseIcon";
import HomeIcon from "@/components/icons/HomeIcon";
import CreateProjectIcon from "@/components/icons/CreateProjectIcon";
import CreatePostIcon from "@/components/icons/CreatePostIcon";
import MyPageIcon from "@/components/icons/MyPageIcon";
import LogoutIcon from "@/components/icons/LogoutIcon";
import { useJWTToken, useRemoveJWTToken } from "@/hooks/auth";
import AdminIcon from "@/components/icons/AdminIcon";

// eslint-disable-next-line react/prop-types
const Sidebar = ({ setModalOpen, isPostsPage = false }) => {
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
          <li>
            <button
              onClick={() => {
                setIsOpen(false);
                setModalOpen(true);
              }}
              className={styles.navListButton}
            >
              {isPostsPage ? (
                <>
                  <CreatePostIcon /> Create Post
                </>
              ) : (
                <>
                  <CreateProjectIcon /> Create Project
                </>
              )}
            </button>
          </li>
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
                Admin Page
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

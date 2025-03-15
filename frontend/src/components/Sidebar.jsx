import { useNavigate } from "react-router-dom";
import styles from "@/styles/LandingPage.module.css";
import Logo from "@/components/Logo";
import HomeIcon from "@/components/icons/HomeIcon";
import CreatePostIcon from "@/components/icons/CreatePostIcon";
import MyPageIcon from "@/components/icons/MyPageIcon";
import LogoutIcon from "@/components/icons/LogoutIcon";

const Sidebar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate("/", { replace: true });
  };

  return (
    <aside className={styles.sidebar}>
      <Logo />
      <nav>
        <ul className={styles.navList}>
          <li>
            <button
              onClick={() => navigate('/landing')}
              className={styles.navListButton}>
              <HomeIcon /> Home
            </button>
          </li>
          <li>
            <button
              // TODO - to uncomment once create post page is up
              // onClick={() => navigate('/create')}
              className={styles.navListButton}>
              <CreatePostIcon /> Create Post
            </button>
          </li>
          <li>
            <button
              // TODO - to uncomment once create post page is up
              // onClick={() => navigate('/project')}
              className={styles.navListButton}>
              <MyPageIcon /> My Page
            </button>
          </li>
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

import { useJWTToken } from "@/hooks/auth";
import { Link } from "react-router-dom";

import styles from "./Logo.module.css";

const Logo = () => {
  const { data: jwtToken } = useJWTToken()

  return (
    <>
      <Link to={jwtToken ? '/landing' : '/'}>
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International"
          className={styles.organizationLogo}
          loading="lazy"
        />
      </Link>
      <p className={`${styles.description} ${styles.hiddenOnMobile}`}>
        The On-Demand Youth Leadership Program is an initiative of the U.S.
        Department of State’s Bureau of Educational and Cultural Affairs (ECA)
        administered by Legacy International
      </p>
    </>
  );
};

export default Logo;

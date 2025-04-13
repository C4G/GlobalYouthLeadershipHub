import styles from "@/styles/pages/UnauthorizedPage.module.css"
import { useNavigate } from "react-router-dom"
import { useRemoveJWTToken } from "@/hooks/auth";

const UnauthorizedPage = () => {
    const navigate = useNavigate()
    const { mutate: removeJWTToken } = useRemoveJWTToken();

    const handleLogout = () => {
        removeJWTToken();
        navigate("/", { replace: true });
    };

    return (
        <div className={styles.container}>
            <h1>Sorry you are not yet verified 🚫</h1>
            <p>Kindly inform your admin to approve your account 🙇‍♂️</p>
            <button className={styles.logoutButton} onClick={handleLogout}>
                Click to return to Login Page
            </button>
        </div>
    )
}

export default UnauthorizedPage
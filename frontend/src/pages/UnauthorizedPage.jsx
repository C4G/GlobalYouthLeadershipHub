import { useNavigate } from "react-router-dom"
import { useRemoveJWTToken } from "@/hooks/auth";
import styles from "./UnauthorizedPage.module.css"

const UnauthorizedPage = () => {
    const navigate = useNavigate()
    const { mutate: removeJWTToken } = useRemoveJWTToken();

    const handleLogout = () => {
        removeJWTToken();
        navigate("/", { replace: true });
    };

    return (
        <div className={styles.container}>
            <h1>Your account is currently pending verification</h1>
            <p>Please contact your administrator for approval if this persists after 3-5 days 🙇‍♂️</p>
            <button className={styles.logoutButton} onClick={handleLogout}>
                Click to return to Login Page
            </button>
        </div>
    )
}

export default UnauthorizedPage
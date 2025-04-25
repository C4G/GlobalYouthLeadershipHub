/* eslint-disable react/prop-types */
import { useNavigate } from "react-router-dom";

import styles from "./Fallback.module.css"

const Fallback = ({ resetErrorBoundary }) => {
    const navigate = useNavigate()

    const handleClick = () => {
        resetErrorBoundary()
        navigate("/landing")
    }

    return (
        <div className={styles.container}>
            <h1>Something Went Wrong 🛑</h1>
            <p>Kindly inform the developer to investigate the error 🙇‍♂️</p>
            <button className={styles.linkButton} onClick={handleClick}>
                Click to return to Main Page
            </button>
        </div>
    );
}

export default Fallback
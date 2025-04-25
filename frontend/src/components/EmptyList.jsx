/* eslint-disable react/prop-types */
import styles from "./EmptyList.module.css"

const EmptyList = ({ text }) => {
    return (
        <div className={styles.container}>
            <h3 className={styles.message}>{text}</h3>
        </div>
    )
}

export default EmptyList
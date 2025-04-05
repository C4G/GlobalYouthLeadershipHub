/* eslint-disable react/prop-types */
import styles from "@/styles/components/Container.module.css"

const Container = ({ children }) => {
    return (
        <div className={styles.container}>
            {children}
        </div >
    )
}

export default Container
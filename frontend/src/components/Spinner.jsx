import styles from "@/styles/components/Spinner.module.css"

// eslint-disable-next-line react/prop-types
const Spinner = ({ text }) => {
    return (
        <>
            <div className={styles.spinnerContainer}>
                <div className={styles.spinner}></div>
                <h1>{text}</h1>
            </div>
        </>
    )
}

export default Spinner
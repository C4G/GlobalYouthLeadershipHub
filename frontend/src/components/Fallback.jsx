import styles from "@/styles/components/Fallback.module.css"

const Fallback = () => {
    return (
        <div className={styles.container}>
            <h1>Something Went Wrong 🛑</h1>
            <p>Kindly inform the developer to investigate the error 🙇‍♂️</p>
        </div>
    );
}

export default Fallback
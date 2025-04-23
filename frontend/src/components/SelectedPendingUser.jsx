/* eslint-disable react/prop-types */
import { useVerifyPendingUser } from "@/hooks/users"
import styles from "@/styles/components/SelectedPendingUser.module.css"

const mapStatus = (status) => {
    if (status === "pending_review") {
        return "Unverified"
    }

    return "Unknown"
}

const SelectedPendingUser = ({ user, refetchUnverifiedUsers }) => {
    const { mutate: verifyUser, isPending } = useVerifyPendingUser()

    if (user === null) {
        return (
            <h3 className={styles.selectUser}>
                Please select a pending user to verify
            </h3>
        )
    }

    const { id, email, firstName, lastName, role: status } = user
    const handleSubmitVerification = () => {
        verifyUser(
            { email },
            {
                onSuccess: (data) => {
                    alert(data.message)
                    refetchUnverifiedUsers()
                },
                onError: (error) => {
                    console.error("Verification failed", error.message)
                }
            }
        )
    }

    return (
        <>
            <h2>Selected Pending User Profile</h2>
            <div className={styles.verifySection} key={id}>
                <p><strong>Name:</strong> {firstName} {lastName} </p>
                <p><strong>Email:</strong> {email} </p>
                <p><strong>Status:</strong> {mapStatus(status)} </p>
                <button onClick={(email) => handleSubmitVerification(email)} disabled={isPending}>
                    {isPending ? "Verifying User..." : "Verify User"}
                </button>
            </div>
        </>
    )
}

export default SelectedPendingUser
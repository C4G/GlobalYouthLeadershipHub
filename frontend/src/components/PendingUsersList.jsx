import { useGetUnverifiedUsers } from "@/hooks/users";
import Spinner from "@/components/Spinner";

import styles from "./PendingUsersList.module.css"

// eslint-disable-next-line react/prop-types
const PendingUsersList = ({ onSelect }) => {
    const { isLoading, data: unverifiedUsers } = useGetUnverifiedUsers();

    if (isLoading) return <Spinner text={"Loading..."} />;

    return (
        <>
            <h2>Pending Users</h2>
            <div className={styles.userList}>
                <ul>
                    {unverifiedUsers.map(user => <li key={user.id} onClick={() => onSelect(user)}>{user.email}</li>)}
                </ul>
            </div>
        </>
    )
}

export default PendingUsersList
import styles from "@/styles/components/CreatePost.module.css"
import { useMutation } from "@tanstack/react-query"
import { useState } from "react"

const CreatePost = () => {
    const [openForm, setOpenForm] = useState(false)

    const mutation = useMutation({
        mutationFn: async () => { },
        onSuccess: () => setOpenForm(prev => !prev),
        onError: () => { }
    })


    const handleSubmit = async (e) => {
        e.preventDefault()
        mutation.mutate()
    }

    return (
        <>
            {!openForm &&
                <div className={styles.postCard}>
                    <button
                        className={styles.createPostButton}
                        onClick={() => setOpenForm(!openForm)}>
                        Create New Post
                    </button>
                </div>
            }

            {openForm &&
                <div className={styles.formContainer}>
                    <form className={styles.form}>
                        <h3>Create a Post</h3>
                        <input type="text" placeholder="Post Name" required />
                        <textarea placeholder="Post Description"></textarea>
                        <button type="submit" onSubmit={handleSubmit}>Submit</button>
                        <button type="button" onClick={() => setOpenForm(prev => !prev)}>Cancel</button>
                    </form>
                </div>
            }
        </>
    )
}

export default CreatePost
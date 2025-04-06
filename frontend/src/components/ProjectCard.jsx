/* eslint-disable react/prop-types */
import { useNavigate } from "react-router-dom"
import styles from "@/styles/components/ProjectCard.module.css"
import { truncateOwnerName, dateStringToLocaleString } from "@/utils/utils"
import { useGetProjectImageById } from "@/hooks/projects"
import ThrashIcon from "@/components/icons/ThrashIcon"
import ArrowIcon from "@/components/icons/ArrowIcon"

const ProjectCard = ({ project }) => {
    const navigate = useNavigate()
    const { id, projectOwner, name, description, projectImageUrl, lastModifiedDate, createdDate } = project
    const { data: imageUrl, isLoading: isImageLoading } = useGetProjectImageById(projectImageUrl);

    const onLinkToPost = (id) => {
        navigate(`/projects/${id}/posts`)
    }

    const handleDelete = (id) => {
        console.log('post deleted', id)
    }

    return (
        <div className={styles.projectCard}>
            <div className={styles.projectHeader}>
                <div className={styles.avatar} aria-hidden="true">
                    {truncateOwnerName(projectOwner)}
                </div>

                <div className={styles.projectInfo}>
                    <h2 className={styles.projectName}>{name}</h2>
                </div>

                <div className={styles.projectActionButtons}>
                    <button className={styles.deleteButton} onClick={() => handleDelete(id)}>
                        <ThrashIcon />
                    </button>
                    <button className={styles.arrowButton} onClick={() => onLinkToPost(id)}>
                        <ArrowIcon />
                    </button>
                </div>
            </div>

            <p className={styles.projectContent}>{description}</p>

            <div className={styles.photoContainer}>
                {isImageLoading ? <div className={styles.loaderSpinner} /> :
                    <img
                        className={styles.photo}
                        src={imageUrl || "/project_fallback.jpeg"}
                        // to handle on first render where this it is showing broken image
                        onError={(e) => (e.currentTarget.src = "/project_fallback.jpeg")}
                    />
                }
            </div>

            <p className={styles.projectUpdatedAt}>
                Last Modified: {dateStringToLocaleString(lastModifiedDate) || dateStringToLocaleString(createdDate)}
            </p>
        </div>
    )
}

export default ProjectCard
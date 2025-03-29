/* eslint-disable react/prop-types */

import styles from "@/styles/components/ProjectCard.module.css"

const ProjectCard = ({ project }) => {
    const { id, createdBy, name, description, weblinkLink, updatedAt } = project

    const onLinkToPost = (id) => {
        console.log('hello world', id)
    }

    return (
        <div className={styles.projectCard} onClick={() => onLinkToPost(id)}>
            <div className={styles.projectHeader}>
                <div className={styles.avatar} aria-hidden="true">
                    {createdBy ?? "AS"}
                </div>

                <div className={styles.projectInfo}>
                    <h2 className={styles.projectName}>{name ?? "Project Unknown"}</h2>
                </div>
            </div>

            <p className={styles.projectContent}>{description}</p>

            <div className={styles.photoContainer}>
                <img src={`blob:${weblinkLink}`} className={styles.photo} />
            </div>

            <p className={styles.projectUpdatedAt}>Last Updated: {updatedAt ?? new Date().toISOString()}</p>
        </div>
    )

}

export default ProjectCard
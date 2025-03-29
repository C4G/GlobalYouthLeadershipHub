/* eslint-disable react/prop-types */
import { useState } from "react";
import styles from "@/styles/components/CreateProject.module.css";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";

const CreateProject = ({ onClose }) => {
  const [projectName, setProjectName] = useState("");
  const [projectText, setProjectText] = useState("");
  const [image, setImage] = useState(null);

  const [error, setError] = useState("");

  const mutation = useMutation({
    mutationFn: async (projectData) => await customFetcher("/projects", "POST", null, projectData),
    onSuccess: () => onClose(),
    onError: (error) => setError(error.message || "Failed to Create Project")
  })

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const newImageURL = URL.createObjectURL(file)
      setImage(newImageURL);

      // This is to prevent memory leaks
      return () => URL.revokeObjectURL(newImageURL);
    }
  };

  const handleSubmitProject = () => {
    if (projectName.trim() === "" || !image) {
      setError("Please enter a project name");
      return;
    }

    if (projectText.trim() === "") {
      setError("Please enter post content");
      return;
    }

    if (!image) {
      setError("Please upload an image");
      return;
    }

    setError("")

    const projectData = {
      userId: 1,
      name: projectName,
      description: projectText,
      weblinkLink: image.replace("blob:", "")
    }

    mutation.mutate(projectData)
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h2>Create a Project</h2>

        <input
          type="text"
          className={styles.inputField}
          placeholder="Enter project title"
          value={projectName}
          onChange={(e) => setProjectName(e.target.value)}
        />

        <textarea
          className={styles.textInput}
          placeholder="What's on your mind?"
          value={projectText}
          onChange={(e) => setProjectText(e.target.value)}
        />

        <label className={styles.fileInputLabel}>
          <input type="file" accept="image/*" onChange={handleImageChange} />
        </label>

        {image && (
          <img src={image} alt="Preview" className={styles.previewImage} />
        )}

        {error && (
          <p className={styles.errorMessage}>{error}</p>
        )}

        <div className={styles.buttonContainer}>
          <button className={styles.cancelButton} onClick={onClose}>
            Cancel
          </button>
          <button className={styles.confirmButton} onClick={handleSubmitProject}>
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateProject;

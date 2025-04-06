/* eslint-disable react/prop-types */
import { useState } from "react";
import styles from "@/styles/components/CreateProject.module.css";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";

const CreateProject = ({ onClose }) => {
  const [projectName, setProjectName] = useState("");
  const [projectDesc, setProjectDesc] = useState("");
  // handle the actual file for upload
  const [imageFile, setImageFile] = useState(null);
  // handle the file for image preview on frontend
  const [imageFilePreview, setImageFilePreview] = useState(null);

  const [error, setError] = useState("");

  const mutation = useMutation({
    mutationFn: async (projectData) => await customFetcher("/projects", "POST", null, projectData),
    onSuccess: () => onClose(),
    onError: (error) => setError(error.message || "Failed to Create Project")
  })

  const handleImageChange = (event) => {
    const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB (in bytes)

    const file = event.target.files[0];

    if (!file) {
      setError("No file selected.");
      return;
    }

    if (file.size > MAX_FILE_SIZE) {
      setError("File Size is above 10MB")
      return
    }

    // store image file for upload
    setImageFile(file)

    // create a blob so that it can be used to preview the image
    const newImagePreviewURL = URL.createObjectURL(file)
    setImageFilePreview(newImagePreviewURL);

    // This is to prevent memory leaks
    return () => URL.revokeObjectURL(newImagePreviewURL);
  };

  const handleSubmitProject = () => {
    if (projectName.trim() === "") {
      setError("Please enter a project name");
      return;
    }

    if (projectDesc.trim() === "") {
      setError("Please enter post content");
      return;
    }

    if (!imageFile) {
      setError("Please upload an image");
      return;
    }

    setError("")

    const projectFormData = new FormData()
    const projectData = {
      name: projectName,
      description: projectDesc,
      file: imageFile
    }
    Object.
      entries(projectData).
      forEach(([key, value]) => projectFormData.append(key, value))

    mutation.mutate(projectFormData)
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h2>Create a Project</h2>

        <input
          type="text"
          className={styles.inputField}
          placeholder="Enter title of your project"
          value={projectName}
          onChange={(e) => setProjectName(e.target.value)}
        />

        <textarea
          className={styles.textInput}
          placeholder="Describe your project — What is it about? Include your goals, achievements, or current progress."
          value={projectDesc}
          onChange={(e) => setProjectDesc(e.target.value)}
        />

        <label className={styles.fileInputLabel}>
          <input type="file" accept="image/*" onChange={handleImageChange} />
        </label>

        {imageFilePreview && (
          <img src={imageFilePreview} alt="Preview" className={styles.previewImage} />
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

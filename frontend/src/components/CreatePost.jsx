/* eslint-disable react/prop-types */
import { useState } from "react";
import { useMutation } from "@tanstack/react-query"
import customFetcher from "@/services/api";

import styles from "./CreatePost.module.css"

const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB (in bytes)

const CreatePost = ({ projectId, onClose }) => {
  const [postTitle, setPostTitle] = useState("");
  const [postDesc, setPostDesc] = useState("");
  // handle the actual file for upload
  const [postImages, setPostImages] = useState([]);
  // handle the file for image preview on frontend
  const [postImagesPreview, setPostImagesPreview] = useState([]);
  const [error, setError] = useState("");

  const mutation = useMutation({
    mutationFn: async (postFormData) => await customFetcher(`/projects/${projectId}/posts`, "POST", null, postFormData),
    onSuccess: () => onClose(),
    onError: (error) => setError(error.message || "Failed to Create Post")
  })

  const handlePostImageChange = (event) => {
    const postImgFiles = Array.from(event.target.files)
    const validMimeTypes = ["image/jpeg", "image/png"]

    const validPostImgFiles = postImgFiles.filter(file => validMimeTypes.includes(file.type) && file.size <= MAX_FILE_SIZE)
    if (validPostImgFiles.length != postImgFiles.length) {
      setError("Files must be JPEG/PNG and under the size of 10MB")
      return
    }
    // store post image files for upload
    setPostImages(validPostImgFiles)

    // create blobs so that it can be used to preview the postImgs
    const previewPostImgURLs = validPostImgFiles.map(imgFile => URL.createObjectURL(imgFile))
    setPostImagesPreview(previewPostImgURLs)

    // This is to prevent memory leaks
    return (() => {
      previewPostImgURLs.forEach(url => URL.revokeObjectURL(url))
    })
  };


  const handleSubmitPost = () => {
    if (postTitle.trim() === "") {
      setError("Please enter a post title");
      return;
    }

    if (postDesc.trim() === "") {
      setError("Please enter post description");
      return;
    }

    // to handle post image that was uploaded
    if (postImages && postImages.MAX_FILE_SIZE) {
      setError("Selected post image exceeds 10MB.");
      return;
    }

    setError("")

    const postFormData = new FormData()
    const postData = {
      title: postTitle,
      content: postDesc,
      ...(postImages.length > 0 && { images: postImages })
    }

    Object.
      entries(postData).
      forEach(([key, value]) => {
        if (key === "images" && Array.isArray(value)) {
          value.forEach(file => postFormData.append(key, file))
        } else {
          postFormData.append(key, value)
        }
      })
    mutation.mutate(postFormData)
  };

  return (
    <div className={styles.modalOverlay}>

      <div className={styles.modal}>
        <h2 className={styles.modalTitle}>Create a Post</h2>

        <input
          type="text"
          className={styles.inputPostTitleField}
          placeholder="Enter title of a post"
          value={postTitle}
          onChange={(e) => setPostTitle(e.target.value)}
        />

        <textarea
          className={styles.textPostDescInput}
          placeholder="Share your journey - updates, milestones, challenges, insights, or anything that could spark connection and inspiration among your fellow alumni."
          value={postDesc}
          onChange={(e) => setPostDesc(e.target.value)}
        />

        <label className={styles.fileInputLabel}>
          <input type="file" accept="image/*" onChange={handlePostImageChange} multiple />
        </label>

        <label className={styles.fileSizeLabel}>
          <p>Each file size should not exceed 10MB</p>
        </label>

        {postImagesPreview.length > 0 && (
          <div className={styles.previewImageGrid}>
            {postImagesPreview.map((src, i) => (
              <img key={i} src={src} alt={`preview ${i}`} className={styles.previewImage} />
            ))}
          </div>
        )}

        {error && (
          <p className={styles.errorMessage}>{error}</p>
        )}

        <div className={styles.buttonContainer}>
          <button className={styles.cancelButton} onClick={onClose}>
            Cancel
          </button>
          <button className={styles.confirmButton} onClick={handleSubmitPost}>
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
}

export default CreatePost
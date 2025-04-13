/* eslint-disable react/prop-types */
import { useState } from "react";
import styles from "@/styles/components/CreatePost.module.css"
// import { useMutation } from "@tanstack/react-query"

const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB (in bytes)

const CreatePost = ({ onClose, onCreate }) => {
  const [postTitle, setPostTitle] = useState("");
  const [postDesc, setPostDesc] = useState("");
  // handle the actual file for upload
  const [postImage, setPostImage] = useState(null);
  // handle the file for image preview on frontend
  const [postImagePreview, setPostImagePreview] = useState(null);
  const [error, setError] = useState("");

  // TODO - retain this for API integration
  //   const mutation = useMutation({
  //     mutationFn: async () => { },
  //     onSuccess: () => setOpenForm(prev => !prev),
  //     onError: () => { }
  // })

  const handlePostImageChange = (event) => {
    const postImgFile = event.target.files[0];
    // TODO - to validate the file type
    // if (!postImgFile) {
    //   setError("No file selected.");
    //   return;
    // }

    if (postImgFile.size > MAX_FILE_SIZE) {
      setError("File size exceeds 10MB")
      return
    }

    // store post image file for upload
    setPostImage(postImgFile)

    // create a blob so that it can be used to preview the postImg
    const newPostPreviewImageURL = URL.createObjectURL(postImgFile)
    setPostImagePreview(newPostPreviewImageURL);

    // This is to prevent memory leaks
    return () => URL.revokeObjectURL(newPostPreviewImageURL);
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

    if (postImage && postImage.MAX_FILE_SIZE) {
      setError("Selected post image exceeds 10MB.");
      return;
    }

    setError("")

    // TODO - to update with API integration
    const postData = {
      id: 1,
      userId: 1,
      postTitle: postTitle,
      description: postDesc,
      weblinkLink: postImage
    }

    onCreate(postData);
    onClose();
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
          <input type="file" accept="image/*" onChange={handlePostImageChange} />
        </label>

        <label className={styles.fileSizeLabel}>
          <p>File size should not exceed 10MB</p>
        </label>

        {postImagePreview && (
          <img src={postImagePreview} alt="Preview" className={styles.previewImage} />
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
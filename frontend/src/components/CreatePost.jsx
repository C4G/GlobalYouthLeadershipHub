import { useState } from "react";
import styles from "@/styles/components/CreatePost.module.css";

const CreatePost = ({ user, onClose, onPostCreate }) => {
  const [projectName, setProjectName] = useState("");
  const [postText, setPostText] = useState("");
  const [image, setImage] = useState(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setImage(URL.createObjectURL(file));
    }
  };

  const handleSubmit = () => {
    if (projectName.trim() === "" || postText.trim() === "" || !image) {
      alert("Please enter a project name, post content, and select an image.");
      return;
    }

    onPostCreate({
      initials: user.name.slice(0, 2).toUpperCase(),
      projectName: projectName,
      content: postText,
      imageSrc: image,
      likes: 0,
      user: user,
    });

    onClose();
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h2>Create a Project</h2>
        <input
          type="text"
          className={styles.inputField}
          placeholder="Enter project name"
          value={projectName}
          onChange={(e) => setProjectName(e.target.value)}
        />
        <textarea
          className={styles.textInput}
          placeholder="What's on your mind?"
          value={postText}
          onChange={(e) => setPostText(e.target.value)}
        />
        <label className={styles.fileInputLabel}>
          <input type="file" accept="image/*" onChange={handleImageChange} />
        </label>
        {image && (
          <img src={image} alt="Preview" className={styles.previewImage} />
        )}
        <div className={styles.buttonContainer}>
          <button className={styles.cancelButton} onClick={onClose}>
            Cancel
          </button>
          <button className={styles.confirmButton} onClick={handleSubmit}>
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreatePost;

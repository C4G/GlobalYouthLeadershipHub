import { useState } from "react";
import styles from "@/styles/components/CreatePost.module.css"
import { useMutation } from "@tanstack/react-query"

const CreatePost = ({ onClose, onCreate }) => {
    const [postName, setPostName] = useState("");
    const [postText, setPostText] = useState("");
    const [image, setImage] = useState(null);
  
    const [error, setError] = useState("");

  // TODO - retain this for API integration
  //   const [openForm, setOpenForm] = useState(false)
  //   const mutation = useMutation({
  //     mutationFn: async () => { },
  //     onSuccess: () => setOpenForm(prev => !prev),
  //     onError: () => { }
  // })

    const handleImageChange = (event) => {
        // TODO - to decide on whether to do some file and size validation
        const file = event.target.files[0];
        if (file) {
          const newImageURL = URL.createObjectURL(file)
          setImage(newImageURL);
    
          // This is to prevent memory leaks
          return () => URL.revokeObjectURL(newImageURL);
        }
      };
    
      const handleSubmitPost = () => {
        if (postName.trim() === "") {
          setError("Please enter a post name");
          return;
        }
    
        if (postText.trim() === "") {
          setError("Please enter post content");
          return;
        }
    
        // TODO - to decided whether to make it optional
        if (!image) {
          setError("Please upload an image");
          return;
        }
    
        setError("")
    
        const postData = {
          id: Date.now(),
          userId: 1,
          name: postName,
          description: postText,
          weblinkLink: image
        }
    
        onCreate(postData);
        onClose();
      };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h2>Create a Post</h2>

        <input
          type="text"
          className={styles.inputField}
          placeholder="Enter post title"
          value={postName}
          onChange={(e) => setPostName(e.target.value)}
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
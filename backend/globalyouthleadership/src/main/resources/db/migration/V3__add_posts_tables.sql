CREATE TABLE `sustSch`.`posts` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `project_id` INT NOT NULL,
    `author_email` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(255) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`project_id`) REFERENCES `sustSch`.`projects`(`id`),
    FOREIGN KEY (`author_email`) REFERENCES `sustSch`.`users`(`email`)
);

CREATE TABLE `sustSch`.`post_images` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `post_id` INT NOT NULL,
    `file_name` VARCHAR(255),
    `file_type` VARCHAR(100),
    `file_data` LONGBLOB,
FOREIGN KEY (`post_id`) REFERENCES `sustSch`.`posts`(`id`)
);

CREATE TABLE `sustSch`.`post_likes` (
    `post_id` INT NOT NULL,
    `user_email` VARCHAR(255) NOT NULL,
    `liked_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`post_id`, `user_email`),
    FOREIGN KEY (`post_id`) REFERENCES `sustSch`.`posts`(`id`),
    FOREIGN KEY (`user_email`) REFERENCES `sustSch`.`users`(`email`)
);

CREATE TABLE `sustSch`.`post_comments` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `post_id` INT NOT NULL,
    `parent_comment_id` INT,
    `user_email` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(255) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`post_id`) REFERENCES `sustSch`.`posts`(`id`),
    FOREIGN KEY (`parent_comment_id`) REFERENCES `sustSch`.`post_comments`(`id`),
    FOREIGN KEY (`user_email`) REFERENCES `sustSch`.`users`(`email`)
);
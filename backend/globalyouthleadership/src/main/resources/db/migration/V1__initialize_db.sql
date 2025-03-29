CREATE SCHEMA IF NOT EXISTS `sustSch`;
use `sustSch`;

CREATE TABLE IF NOT EXISTS `sustSch`.`users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `username` VARCHAR(255),
    `password` VARCHAR(255) NOT NULL,
    `firstname` VARCHAR(100) NOT NULL,
    `lastname` VARCHAR(100) NOT NULL,
    `date_of_birth` DATE NOT NULL,
    `role` ENUM('admin', 'pending_review', 'user') NOT NULL DEFAULT 'pending_review',
    `created_by` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS `sustSch`.`follows` (
    `following_user_id` INT NOT NULL,
    `followed_user_id` INT NOT NULL,
    `created_by` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`following_user_id`, `followed_user_id`),
    FOREIGN KEY (`following_user_id`) REFERENCES `sustSch`.`users` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`followed_user_id`) REFERENCES `sustSch`.`users` (`id`) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS `sustSch`.`projects` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `description` VARCHAR(255),
    `weblink_link` VARCHAR(255),
    `created_by` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `sustSch`.`users` (`id`) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS `sustSch`.`posts` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `post_type` ENUM('public', 'reply') NOT NULL,
    `replied_post_id` INT COMMENT 'Post replied to',
    `private_user` INT COMMENT 'User for private msg',
    `project_id` INT,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL COMMENT 'Content of the post',
    `user_id` INT NOT NULL,
    `status` VARCHAR(255),
    `created_by` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `sustSch`.`users` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`replied_post_id`) REFERENCES `sustSch`.`posts` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`private_user`) REFERENCES `sustSch`.`users` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`project_id`) REFERENCES `sustSch`.`projects` (`id`) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS `sustSch`.`pictures` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT 'id of picture',
    `user_id` INT NOT NULL COMMENT 'id of the user',
    `path` VARCHAR(255),
    `name` VARCHAR(255),
    `created_by` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `sustSch`.`users` (`id`) ON DELETE CASCADE
    );
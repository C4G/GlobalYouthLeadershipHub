-- Drop old table
DROP TABLE IF EXISTS `sustSch`.`follows`;
DROP TABLE IF EXISTS `sustSch`.`posts`;
DROP TABLE IF EXISTS `sustSch`.`pictures`;
DROP TABLE IF EXISTS `sustSch`.`projects`;

-- Recreate table with new schema
CREATE TABLE `sustSch`.`projects` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `project_owner` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `file_name` VARCHAR(255),
    `file_type` VARCHAR(100),
    `file_data` LONGBLOB,
    `created_by` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(255) NOT NULL,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`project_owner`) REFERENCES `sustSch`.`users`(`email`)
);
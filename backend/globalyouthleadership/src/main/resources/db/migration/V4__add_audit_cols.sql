ALTER TABLE `sustSch`.`post_images`
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE `sustSch`.`post_images`
    ADD COLUMN created_by VARCHAR(255) NOT NULL;

ALTER TABLE `sustSch`.`post_images`
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE `sustSch`.`post_images`
    ADD COLUMN updated_by VARCHAR(255) NOT NULL;
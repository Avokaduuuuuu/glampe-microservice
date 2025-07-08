CREATE DATABASE glampe_payment_db;

USE glampe_payment_db;

CREATE TABLE `payment` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_booking` INT,
    `payment_method` VARCHAR(255) DEFAULT "",
    `total_amount` DECIMAL(20,2) DEFAULT 0.0,
    `status` ENUM('Pending', 'Completed', 'Failed'),
    `payment_intent_id` VARCHAR(255),
    `completed_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

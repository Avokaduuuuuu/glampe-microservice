CREATE DATABASE glampe_booking_db;

USE glampe_booking_db;

CREATE TABLE `booking` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_user` INT,
    `id_camp_site` INT,
    `status` ENUM('Pending', 'Deposit', 'Accepted', 'Completed', 'Cancelled', 'Refund', 'Check_In'),
    `check_in_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `check_out_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `total_amount` DECIMAL(20,2) DEFAULT 0.0,
    `system_fee`DECIMAL(20,2) DEFAULT 0.0,
    `net_amount` DECIMAL(20,2) DEFAULT 0.0,
    `comment` VARCHAR(255) DEFAULT "",
    `rating` INT DEFAULT 0,
    `message` text,
    `is_point_used` BOOLEAN NOT NULL DEFAULT false,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    INDEX(`id_user`)
);

CREATE TABLE `booking_detail` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_booking` INT,
    `id_camp_type` INT,
    `id_camp` INT,
    `check_in_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `check_out_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `amount` DECIMAL(20,2) DEFAULT 0.0,
    `add_on` DECIMAL(20,2) DEFAULT 0.0,
    `status` ENUM('Pending', 'Check_In', 'Check_Out') DEFAULT 'Pending',
    
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `booking_detail_order` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) DEFAULT "",
    `quantity` INT DEFAULT 0,
    `price` DECIMAL(20,2) DEFAULT 0.0,
    `total_amount` DECIMAL(20,2) DEFAULT 0.0, 
    `note` text,
    `id_booking_detail` INT
);

CREATE TABLE `booking_selection` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_booking` INT,
    `id_selection` INT,
    `name` VARCHAR(255) DEFAULT "",
    `quantity` INT DEFAULT 0
);

ALTER TABLE `booking_detail` 
	ADD FOREIGN KEY (`id_booking`) REFERENCES `booking` (`id`);

ALTER TABLE `booking_detail_order`
	ADD FOREIGN KEY (`id_booking_detail`) REFERENCES `booking_detail` (`id`);

ALTER TABLE `booking_selection`
	ADD FOREIGN KEY (`id_booking`) REFERENCES `booking` (`id`);
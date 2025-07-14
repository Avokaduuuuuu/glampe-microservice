CREATE DATABASE glampe_camp_site_db;

USE glampe_camp_site_db;

CREATE TABLE `camp_site` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT "",
    `address` VARCHAR(255) DEFAULT "",
    `city` varchar(255) DEFAULT "",
    `latitude` DECIMAL(10,7) DEFAULT 0.0,
    `longitude` DECIMAL(10,7) DEFAULT 0.0,
    `status` ENUM('Pending', 'Unavailable', 'Available', 'Denied'),
    `message` text,
    `deposit_rate` DECIMAL(10,2) DEFAULT 0.0,
    `description` text,
    `id_user` INT,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
    
);

CREATE TABLE `camp` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT "",
    `status` enum("Unavailable", "Unassigned", "Assigned") DEFAULT "Unassigned",
    `id_camp_type` INT,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE `facility` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT "",
    `description` TEXT,
    `image` VARCHAR(255) DEFAULT '',
    `is_deleted` BOOLEAN DEFAULT FALSE,
    
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `camp_type_facility` (
    `id_facility` INT,
    `id_camp_type` INT,
    PRIMARY KEY (`id_facility`, `id_camp_type`)
);

CREATE TABLE `camp_site_gallery` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_camp_site` INT,
    `path` VARCHAR(255) DEFAULT "",
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `report` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `id_camp_site` INT,
    `id_user` INT,
    `status` enum('Pending', 'Resolved', 'Denied') DEFAULT 'Pending',
    `message` TEXT,
    `report_type` VARCHAR(255) DEFAULT "",
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `camp_type` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(255) DEFAULT "",
    `capacity` INT DEFAULT 0,
    `price` DECIMAL(20,2) DEFAULT 0.0,
    `weekend_price` DECIMAL(20,2) DEFAULT 0.0,
    `id_camp_site` INT,
    `quantity` INT DEFAULT 0,
    `image` varchar(255) DEFAULT "",
    `is_deleted` BOOLEAN DEFAULT FALSE,
    
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `custom_price` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`id_camp_type` INT,
	`date` DATE NOT NULL,
	`price` DECIMAL(20,2) DEFAULT 0.0,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `utility` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT "",
    `image` VARCHAR(255) DEFAULT '',
    `is_deleted` BOOLEAN DEFAULT FALSE,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `camp_site_utility` (
    `id_camp_site` INT,
    `id_utility` INT,
    PRIMARY KEY (`id_camp_site`, `id_utility`)
);

CREATE TABLE place_type(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(255) DEFAULT "",
    `image` VARCHAR(255) DEFAULT "",
	`is_deleted` BOOLEAN DEFAULT FALSE,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
	
);

CREATE TABLE camp_site_place_type(
	`id_camp_site` INT,
	`id_place_type` INT,
	
	PRIMARY KEY (`id_camp_site`, `id_place_type`)
);

CREATE TABLE `selection` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT "",
    `description` TEXT,
    `price` DECIMAL(20,2) DEFAULT 0.0,
    `image` VARCHAR(255) DEFAULT '',
    `is_deleted` BOOLEAN DEFAULT FALSE,
    `id_camp_site` INT,
 	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
 	`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `camp_type`
	ADD FOREIGN KEY (`id_camp_site`) REFERENCES `camp_site` (`id`);

ALTER TABLE `camp_site_gallery` 
	ADD FOREIGN KEY (`id_camp_site`) REFERENCES `camp_site` (`id`);

ALTER TABLE `custom_price` 
	ADD FOREIGN KEY (`id_camp_type`) REFERENCES `camp_type` (`id`);

ALTER TABLE `camp`
	ADD FOREIGN KEY (`id_camp_type`) REFERENCES `camp_type` (`id`);

ALTER TABLE `camp_type_facility`
	ADD FOREIGN KEY (`id_camp_type`) REFERENCES `camp_type` (`id`);

ALTER TABLE `camp_type_facility`
	ADD FOREIGN KEY (`id_facility`) REFERENCES `facility` (`id`);

ALTER TABLE `camp_site_utility`
	ADD FOREIGN KEY (`id_camp_site`) REFERENCES `camp_site` (`id`);

ALTER TABLE `camp_site_utility`
	ADD FOREIGN KEY (`id_utility`) REFERENCES `utility` (`id`);

ALTER TABLE `camp_site_place_type`
	ADD FOREIGN KEY (`id_camp_site`) REFERENCES `camp_site` (`id`);

ALTER TABLE `camp_site_place_type`
	ADD FOREIGN KEY (`id_place_type`) REFERENCES `place_type` (`id`);

ALTER TABLE `selection`
	ADD FOREIGN KEY (`id_camp_site`) REFERENCES `camp_site` (`id`);

INSERT INTO `camp_site` (`name`, `address`, `city`, `latitude`, `longitude`, `created_at`, `status`, `id_user`) VALUES
('Pine Grove Glamping', '123 Forest Road, Pine District', 'Hanoi', 21.0245, 105.8412, NOW(), 'Available', 1),
('Lakeside Haven', '456 Lake View Drive', 'Ho Chi Minh City', 10.7769, 106.7983, NOW(), 'Available', 2),
('Mountain Vista', '789 Mountain Path', 'Da Nang', 16.0544, 108.2022, NOW(), 'Available', 3),
('Riverside Retreat', '101 River Lane', 'Hue', 16.4637, 107.5906, NOW(), 'Available', 4),
('Ocean Breeze Camp', '202 Coastal Highway', 'Nha Trang', 12.2388, 109.1967, NOW(), 'Available', 1),
('Desert Oasis', '303 Sandy Road', 'Mui Ne', 10.9326, 108.2871, NOW(), 'Available', 2),
('Forest Canopy', '404 Woodland Trail', 'Da Lat', 11.9404, 108.4583, NOW(), 'Available', 3),
('Valley View', '505 Valley Way', 'Sapa', 22.3364, 103.8438, NOW(), 'Available', 4),
('Sunset Point', '606 Cliff Road', 'Phu Quoc', 10.2231, 103.9570, NOW(), 'Available', 1),
('Starlight Camp', '707 Night Sky Avenue', 'Ha Giang', 22.8233, 104.9784, NOW(), 'Available', 2),
('Meadow Retreat', '808 Flower Field Lane', 'Ninh Binh', 20.2486, 105.9876, NOW(), 'Available', 3),
('Highland Escape', '909 Highland Road', 'Moc Chau', 20.8299, 104.6390, NOW(), 'Available', 4),
('Tranquil Waters', '111 Peaceful Cove', 'Hoi An', 15.8801, 108.3380, NOW(), 'Available', 1),
('Green Valley Camp', '222 Green Path', 'Phong Nha', 17.5848, 106.2838, NOW(), 'Available', 2),
('Misty Hills', '333 Foggy Road', 'Tam Dao', 21.4593, 105.6467, NOW(), 'Available', 3),
('Rocky Peaks', '444 Boulder Way', 'Fansipan', 22.3033, 103.7767, NOW(), 'Available', 4),
('Sandy Beach Glamping', '555 Shore Drive', 'Quy Nhon', 13.7829, 109.2196, NOW(), 'Available', 1),
('Tropical Paradise', '666 Palm Road', 'Con Dao', 8.6815, 106.6201, NOW(), 'Available', 2),
('Sunrise Camp', '777 Eastern Hill', 'Cat Ba', 20.7976, 107.0583, NOW(), 'Available', 3),
('Jungle Adventure', '888 Wild Path', 'Pu Luong', 20.4880, 105.0224, NOW(), 'Available', 4),
('Bamboo Forest Retreat', '999 Bamboo Lane', 'Mai Chau', 20.6627, 105.0566, NOW(), 'Available', 1),
('Eagle Nest', '123 High Point Road', 'Meo Vac', 23.1562, 105.4642, NOW(), 'Available', 2),
('Wildflower Meadows', '234 Petal Path', 'Mu Cang Chai', 21.8518, 104.1495, NOW(), 'Available', 3),
('Crystal Lake Camp', '345 Clear Water Drive', 'Ba Be', 22.4074, 105.6180, NOW(), 'Available', 4),
('Ancient Forest', '456 Old Growth Trail', 'Cuc Phuong', 20.3549, 105.6056, NOW(), 'Available', 1),
('Waterfall Hideaway', '567 Cascade Road', 'Dray Nur', 12.8156, 108.3715, NOW(), 'Available', 2),
('Rice Terrace View', '678 Paddy Field Lane', 'Y Ty', 22.6163, 103.8441, NOW(), 'Available', 3),
('Tea Plantation Glamping', '789 Tea Leaf Road', 'Thai Nguyen', 21.5941, 105.8460, NOW(), 'Available', 4),
('Coffee Hills Camp', '890 Coffee Bean Way', 'Buon Ma Thuot', 12.6792, 108.0507, NOW(), 'Available', 1),
('Vineyard Vista', '901 Grape Road', 'Phan Thiet', 10.9804, 108.2617, NOW(), 'Available', 2),
('Cloud Forest', '112 Mist Trail', 'Bach Ma', 16.1978, 107.8578, NOW(), 'Available', 3),
('Flower Garden Camp', '223 Blossom Road', 'Ha Long', 20.9596, 107.0453, NOW(), 'Available', 4),
('Bird Sanctuary', '334 Wing Path', 'Tram Chim', 10.7131, 105.5565, NOW(), 'Available', 1),
('Butterfly Haven', '445 Flutter Way', 'Cat Tien', 11.4245, 107.4011, NOW(), 'Available', 2),
('Mangrove Maze', '556 Swamp Road', 'Can Gio', 10.4104, 106.9679, NOW(), 'Available', 3),
('Cave Exploration Camp', '667 Underground Trail', 'Son Doong', 17.5149, 106.2824, NOW(), 'Available', 4),
('Sunset Beach', '778 Western Shore', 'Ly Son', 15.3801, 109.1149, NOW(), 'Available', 1),
('Island Paradise', '889 Castaway Road', 'Nam Du', 9.6693, 104.3169, NOW(), 'Available', 2),
('Hidden Cove', '990 Secret Bay', 'Co To', 20.9766, 107.7644, NOW(), 'Available', 3),
('Flamingo Point', '111 Pink Shore', 'Xuan Thuy', 20.2226, 106.5651, NOW(), 'Available', 4),
('Lotus Lake', '222 Floating Petal Road', 'Dong Thap', 10.4946, 105.6886, NOW(), 'Available', 1),
('Cinnamon Forest', '333 Spice Trail', 'Yen Bai', 21.7229, 104.9112, NOW(), 'Available', 2),
('Citrus Grove', '444 Orange Lane', 'Vinh Long', 10.2560, 105.9722, NOW(), 'Available', 3),
('Pepper Farm Stay', '555 Spicy Path', 'Phu Quoc', 10.2295, 103.9591, NOW(), 'Available', 4),
('Coconut Beach', '666 Palm Tree Lane', 'Ben Tre', 10.2433, 106.3755, NOW(), 'Available', 1),
('Lavender Fields', '777 Purple Path', 'Da Lat', 11.9465, 108.4419, NOW(), 'Available', 2),
('Cherry Blossom Hill', '888 Pink Petal Road', 'Moc Chau', 20.8351, 104.6297, NOW(), 'Available', 3),
('Jasmine Garden', '999 Fragrant Way', 'Hue', 16.4543, 107.5658, NOW(), 'Available', 4),
('Blue Lagoon', '101 Azure Cove', 'Quang Ninh', 21.0182, 107.2921, NOW(), 'Available', 1),
('Blue Lagoon', '101 Azure Cove', 'Quang Ninh', 21.0182, 107.2921, NOW(), 'Available', 1);

-- Insert 2 camp types for each campsite (100 camp types total)
INSERT INTO `camp_type` (`type`, `capacity`, `price`, `weekend_price`, `updated_at`, `id_camp_site`, `quantity`) VALUES
-- Campsite 1
('Luxury Tent', 4, 150.00, 180.00, NOW(), 1, 1),
('Standard Tent', 2, 100.00, 120.00, NOW(), 1, 1),
-- Campsite 2
('Lakeside Cabin', 6, 250.00, 300.00, NOW(), 2, 1),
('Water Bungalow', 4, 200.00, 240.00, NOW(), 2, 1),
-- Campsite 3
('Mountain Hut', 4, 180.00, 210.00, NOW(), 3, 1),
('Alpine Tent', 2, 120.00, 150.00, NOW(), 3, 1),
-- Campsite 4
('Riverside Cabin', 6, 220.00, 260.00, NOW(), 4, 1),
('Floating Tent', 2, 150.00, 180.00, NOW(), 4, 1),
-- Campsite 5
('Beach Villa', 8, 350.00, 420.00, NOW(), 5, 1),
('Beach Tent', 4, 180.00, 220.00, NOW(), 5, 1),
-- Campsite 6
('Desert Dome', 4, 200.00, 240.00, NOW(), 6, 1),
('Bedouin Tent', 6, 230.00, 275.00, NOW(), 6, 1),
-- Campsite 7
('Treehouse', 4, 280.00, 340.00, NOW(), 7, 1),
('Forest Tent', 2, 130.00, 160.00, NOW(), 7, 1),
-- Campsite 8
('Valley Lodge', 8, 320.00, 380.00, NOW(), 8, 1),
('Hillside Tent', 4, 170.00, 210.00, NOW(), 8, 1),
-- Campsite 9
('Cliff House', 6, 290.00, 350.00, NOW(), 9, 1),
('Panorama Tent', 2, 160.00, 200.00, NOW(), 9, 1),
-- Campsite 10
('Star Dome', 4, 220.00, 270.00, NOW(), 10, 1),
('Astro Tent', 2, 140.00, 170.00, NOW(), 10, 1),
-- Campsite 11
('Meadow Cabin', 6, 210.00, 250.00, NOW(), 11, 1),
('Garden Tent', 4, 150.00, 180.00, NOW(), 11, 1),
-- Campsite 12
('Highland Lodge', 8, 340.00, 410.00, NOW(), 12, 1),
('Mountain View Tent', 4, 190.00, 230.00, NOW(), 12, 1),
-- Campsite 13
('Water Cabin', 6, 270.00, 330.00, NOW(), 13, 1),
('Lagoon Tent', 2, 160.00, 190.00, NOW(), 13, 1),
-- Campsite 14
('Valley Lodge', 8, 310.00, 370.00, NOW(), 14, 1),
('Eco Tent', 4, 170.00, 210.00, NOW(), 14, 1),
-- Campsite 15
('Misty Cabin', 6, 230.00, 280.00, NOW(), 15, 1),
('Fog Tent', 2, 140.00, 170.00, NOW(), 15, 1),
-- Campsite 16
('Rock Lodge', 4, 260.00, 310.00, NOW(), 16, 1),
('Boulder Tent', 2, 150.00, 180.00, NOW(), 16, 1),
-- Campsite 17
('Beach House', 8, 380.00, 450.00, NOW(), 17, 1),
('Sand Tent', 4, 200.00, 240.00, NOW(), 17, 1),
-- Campsite 18
('Palm Bungalow', 6, 290.00, 350.00, NOW(), 18, 1),
('Island Tent', 4, 180.00, 220.00, NOW(), 18, 1),
-- Campsite 19
('Dawn Villa', 6, 320.00, 380.00, NOW(), 19, 1),
('Sunrise Tent', 2, 170.00, 210.00, NOW(), 19, 1),
-- Campsite 20
('Jungle Lodge', 8, 340.00, 410.00, NOW(), 20, 1),
('Adventure Tent', 4, 190.00, 230.00, NOW(), 20, 1),
-- Campsite 21
('Bamboo Hut', 4, 210.00, 250.00, NOW(), 21, 1),
('Grove Tent', 2, 130.00, 160.00, NOW(), 21, 1),
-- Campsite 22
('Mountain Peak Cabin', 6, 300.00, 360.00, NOW(), 22, 1),
('Summit Tent', 4, 180.00, 220.00, NOW(), 22, 1),
-- Campsite 23
('Flower Field Cottage', 4, 240.00, 290.00, NOW(), 23, 1),
('Meadow Tent', 2, 150.00, 180.00, NOW(), 23, 1),
-- Campsite 24
('Lakefront Cabin', 6, 280.00, 340.00, NOW(), 24, 1),
('Lake View Tent', 4, 170.00, 210.00, NOW(), 24, 1),
-- Campsite 25
('Forest Lodge', 8, 330.00, 400.00, NOW(), 25, 1),
('Woodland Tent', 4, 190.00, 230.00, NOW(), 25, 1),
-- Campsite 26
('Waterfall Cabin', 6, 310.00, 370.00, NOW(), 26, 1),
('Cascade Tent', 2, 180.00, 220.00, NOW(), 26, 1),
-- Campsite 27
('Rice Field Villa', 8, 350.00, 420.00, NOW(), 27, 1),
('Terrace View Tent', 4, 200.00, 240.00, NOW(), 27, 1),
-- Campsite 28
('Tea House', 6, 270.00, 330.00, NOW(), 28, 1),
('Plantation Tent', 4, 160.00, 190.00, NOW(), 28, 1),
-- Campsite 29
('Coffee Lodge', 8, 320.00, 380.00, NOW(), 29, 1),
('Bean Estate Tent', 4, 180.00, 220.00, NOW(), 29, 1),
-- Campsite 30
('Vineyard Cottage', 6, 290.00, 350.00, NOW(), 30, 1),
('Grape View Tent', 2, 170.00, 210.00, NOW(), 30, 1),
-- Campsite 31
('Cloud Cabin', 4, 260.00, 310.00, NOW(), 31, 1),
('Mist Tent', 2, 150.00, 180.00, NOW(), 31, 1),
-- Campsite 32
('Garden Villa', 8, 370.00, 440.00, NOW(), 32, 1),
('Blossom Tent', 4, 210.00, 250.00, NOW(), 32, 1),
-- Campsite 33
('Bird Watch Lodge', 6, 280.00, 340.00, NOW(), 33, 1),
('Aviary Tent', 2, 160.00, 190.00, NOW(), 33, 1),
-- Campsite 34
('Butterfly Cottage', 4, 240.00, 290.00, NOW(), 34, 1),
('Flutter Tent', 2, 140.00, 170.00, NOW(), 34, 1),
-- Campsite 35
('Mangrove House', 6, 270.00, 330.00, NOW(), 35, 1),
('Swamp View Tent', 4, 160.00, 190.00, NOW(), 35, 1),
-- Campsite 36
('Cave Lodge', 4, 300.00, 360.00, NOW(), 36, 1),
('Grotto Tent', 2, 180.00, 220.00, NOW(), 36, 1),
-- Campsite 37
('Beach Front Villa', 8, 390.00, 470.00, NOW(), 37, 1),
('Sunset View Tent', 4, 220.00, 270.00, NOW(), 37, 1),
-- Campsite 38
('Island Bungalow', 6, 340.00, 410.00, NOW(), 38, 1),
('Ocean View Tent', 2, 190.00, 230.00, NOW(), 38, 1),
-- Campsite 39
('Cove Cottage', 4, 260.00, 310.00, NOW(), 39, 1),
('Bay View Tent', 2, 150.00, 180.00, NOW(), 39, 1),
-- Campsite 40
('Flamingo Lodge', 6, 310.00, 370.00, NOW(), 40, 1),
('Pink Shore Tent', 4, 180.00, 220.00, NOW(), 40, 1),
-- Campsite 41
('Lotus House', 4, 270.00, 330.00, NOW(), 41, 1),
('Water Lily Tent', 2, 160.00, 190.00, NOW(), 41, 1),
-- Campsite 42
('Cinnamon Lodge', 6, 290.00, 350.00, NOW(), 42, 1),
('Spice Trail Tent', 4, 170.00, 210.00, NOW(), 42, 1),
-- Campsite 43
('Citrus Grove Cabin', 4, 250.00, 300.00, NOW(), 43, 1),
('Orchard Tent', 2, 140.00, 170.00, NOW(), 43, 1),
-- Campsite 44
('Pepper Farm Lodge', 6, 280.00, 340.00, NOW(), 44, 1),
('Spicy View Tent', 4, 160.00, 190.00, NOW(), 44, 1),
-- Campsite 45
('Coconut Beach House', 8, 360.00, 430.00, NOW(), 45, 1),
('Palm View Tent', 4, 200.00, 240.00, NOW(), 45, 1),
-- Campsite 46
('Lavender Cottage', 4, 240.00, 290.00, NOW(), 46, 1),
('Purple Field Tent', 2, 140.00, 170.00, NOW(), 46, 1),
-- Campsite 47
('Cherry Blossom Villa', 6, 320.00, 380.00, NOW(), 47, 1),
('Pink Hill Tent', 4, 180.00, 220.00, NOW(), 47, 1),
-- Campsite 48
('Jasmine House', 4, 260.00, 310.00, NOW(), 48, 1),
('Fragrant Garden Tent', 2, 150.00, 180.00, NOW(), 48, 1),
-- Campsite 49
('Blue Lagoon Villa', 8, 380.00, 450.00, NOW(), 49, 1),
('Azure Cove Tent', 4, 210.00, 250.00, NOW(), 49, 1),
-- Campsite 50
('Waterfront Lodge', 6, 330.00, 400.00, NOW(), 50, 1),
('River View Tent', 2, 190.00, 230.00, NOW(), 50, 1);
-- Insert 1 camp for each camp type (100 camps total)
-- We'll create a loop to generate these dynamically based on the camp_type IDs
DELIMITER $$

CREATE PROCEDURE insert_camps()
BEGIN
    DECLARE camp_id INT DEFAULT 1;
    DECLARE camp_type_id INT DEFAULT 1;

    WHILE camp_type_id <= 100 DO
        INSERT INTO `camp` (`id`, `name`, `created_at`, `updated_at`, `id_camp_type`)
        SELECT 
            camp_id, CONCAT(type, ' #1'), NOW(), NOW(), camp_type_id
        FROM `camp_type`
        WHERE `id` = camp_type_id;

        SET camp_id = camp_id + 1;
        SET camp_type_id = camp_type_id + 1;
    END WHILE;
END $$

DELIMITER ;

-- Then call it:
CALL insert_camps();

INSERT INTO `facility` (`id`, `name`, `description`, `image`, `is_deleted`) VALUES
(1, 'WiFi', 'High-speed internet access throughout the property', 'wifi.jpg', FALSE),
(2, 'Hot Tub', 'Private relaxing hot water tub', 'hot_tub.jpg', FALSE),
(3, 'Private Bathroom', 'En-suite bathroom facilities', 'bathroom.jpg', FALSE),
(4, 'Air Conditioning', 'Climate control for your comfort', 'ac.jpg', FALSE),
(5, 'Heating', 'Stay warm during cooler nights', 'heating.jpg', FALSE),
(6, 'Kitchen', 'Fully equipped kitchen for self-catering', 'kitchen.jpg', FALSE),
(7, 'BBQ Area', 'Outdoor barbecue facilities', 'bbq.jpg', FALSE),
(8, 'Swimming Pool', 'Shared swimming pool', 'pool.jpg', FALSE),
(9, 'Private Pool', 'Exclusive pool for your accommodation', 'private_pool.jpg', FALSE),
(10, 'Parking', 'On-site parking available', 'parking.jpg', FALSE),
(11, 'Pet Friendly', 'Pets are welcome', 'pet.jpg', FALSE),
(12, 'Wheelchair Accessible', 'Facilities for guests with mobility needs', 'wheelchair.jpg', FALSE),
(13, 'Fireplace', 'Indoor fireplace for cozy evenings', 'fireplace.jpg', FALSE),
(14, 'TV', 'Television with local channels', 'tv.jpg', FALSE),
(15, 'Board Games', 'Selection of games for entertainment', 'games.jpg', FALSE),
(16, 'Washing Machine', 'Laundry facilities available', 'laundry.jpg', FALSE),
(17, 'Coffee Maker', 'Make your own fresh coffee', 'coffee.jpg', FALSE),
(18, 'Balcony', 'Private outdoor seating area', 'balcony.jpg', FALSE),
(19, 'Security System', '24/7 security for peace of mind', 'security.jpg', FALSE),
(20, 'Fire Pit', 'Outdoor fire pit for evening enjoyment', 'fire_pit.jpg', FALSE);

-- Insert place types
INSERT INTO `place_type` (`id`, `name`, `is_deleted`) VALUES
(1, 'River View', FALSE),
(2, 'Lake View', FALSE),
(3, 'Mountain View', FALSE),
(4, 'Ocean View', FALSE),
(5, 'Desert', FALSE),
(6, 'Forest', FALSE),
(7, 'Farmland', FALSE),
(8, 'Vineyard', FALSE),
(9, 'Beach Front', FALSE),
(10, 'Jungle', FALSE),
(11, 'Island', FALSE),
(12, 'Rice Fields', FALSE),
(13, 'Tea Plantation', FALSE),
(14, 'Coffee Plantation', FALSE),
(15, 'Flower Garden', FALSE);



INSERT INTO `utility` (`id`, `name`, `image`, `is_deleted`) VALUES
(1, 'Bar', 'utilities/bar.jpg', FALSE),
(2, 'Parking Area', 'utilities/parking.jpg', FALSE),
(3, 'Waste Disposal', 'utilities/waste.jpg', FALSE),
(4, 'Reception', 'utilities/reception.jpg', FALSE),
(5, 'Restaurant', 'utilities/restaurant.jpg', FALSE),
(6, 'Cafe', 'utilities/cafe.jpg', FALSE),
(7, 'Convenience Store', 'utilities/store.jpg', FALSE),
(8, 'Tour Desk', 'utilities/tour.jpg', FALSE),
(9, 'Bicycle Rental', 'utilities/bicycle.jpg', FALSE),
(10, 'Airport Shuttle', 'utilities/shuttle.jpg', FALSE),
(11, 'Spa', 'utilities/spa.jpg', FALSE),
(12, 'Gym', 'utilities/gym.jpg', FALSE),
(13, 'Children Playground', 'utilities/playground.jpg', FALSE),
(14, 'Yoga Studio', 'utilities/yoga.jpg', FALSE),
(15, 'Laundry Service', 'utilities/laundry.jpg', FALSE);


INSERT INTO `selection` (`name`, `description`, `price`, `image`, `is_deleted`, `updated_at`, `id_camp_site`) VALUES

('Kayak Rental', 'Explore nearby waterways with our stable kayaks. Includes life vests and paddles.', 25.00, 'selections/kayak.jpg', FALSE, NOW(), 1),
('BBQ Package', 'Premium BBQ setup with charcoal, utensils, and a selection of marinades.', 35.00, 'selections/bbq.jpg', FALSE, NOW(), 1),
('Guided Nature Walk', 'Expert-led tour of local flora and fauna. 2 hours duration.', 15.00, 'selections/nature_walk.jpg', FALSE, NOW(), 2),
('Fishing Equipment', 'Complete set of fishing gear for freshwater fishing.', 20.00, 'selections/fishing.jpg', FALSE, NOW(), 2),
('Mountain Bike Rental', 'Quality mountain bikes for exploring trails. Half-day rental.', 18.00, 'selections/bike.jpg', FALSE, NOW(), 3),
('Stargazing Session', 'Guided evening stargazing with telescope and astronomy expert.', 30.00, 'selections/stargazing.jpg', FALSE, NOW(), 3),
('Campfire Package', 'Firewood, seating, marshmallows, and sticks for a perfect evening campfire.', 22.00, 'selections/campfire.jpg', FALSE, NOW(), 4),
('Picnic Basket', 'Gourmet picnic with local delicacies, blanket, and cutlery included.', 40.00, 'selections/picnic.jpg', FALSE, NOW(), 4),
('Surf Lesson', '90-minute surf lesson with professional instructor. Board included.', 45.00, 'selections/surf.jpg', FALSE, NOW(), 5),
('Beach Equipment', 'Beach chairs, umbrella, towels, and cooler for a day at the beach.', 15.00, 'selections/beach.jpg', FALSE, NOW(), 5),
('Sunset Cruise', '2-hour boat cruise to enjoy stunning sunset views.', 50.00, 'selections/cruise.jpg', FALSE, NOW(), 6),
('Photography Tour', 'Guided tour to the best photo spots with photography tips.', 35.00, 'selections/photo.jpg', FALSE, NOW(), 6),
('Bird Watching Kit', 'Binoculars, field guide, and map of local bird watching spots.', 12.00, 'selections/birdwatch.jpg', FALSE, NOW(), 7),
('Hammock Rental', 'Comfortable hammock setup between trees for ultimate relaxation.', 10.00, 'selections/hammock.jpg', FALSE, NOW(), 7),
('Local Cooking Class', 'Learn to prepare traditional dishes with a local chef. Includes meal.', 55.00, 'selections/cooking.jpg', FALSE, NOW(), 8),
('Yoga Session', 'Private yoga session with certified instructor. Mats provided.', 25.00, 'selections/yoga.jpg', FALSE, NOW(), 8),
('Wine Tasting', 'Sampling of local wines with cheese pairing. 1-hour session.', 30.00, 'selections/wine.jpg', FALSE, NOW(), 9),
('Massage Service', 'Relaxing 60-minute massage in the comfort of your accommodation.', 65.00, 'selections/massage.jpg', FALSE, NOW(), 9),
('Rock Climbing', 'Guided rock climbing experience for beginners. Equipment provided.', 40.00, 'selections/climbing.jpg', FALSE, NOW(), 10),
('Pottery Workshop', 'Create your own pottery with guidance from a local artisan.', 35.00, 'selections/pottery.jpg', FALSE, NOW(), 10);


DELIMITER $$

CREATE PROCEDURE insert_selections()
BEGIN
    DECLARE campsite_id INT DEFAULT 11;

    WHILE campsite_id <= 50 DO
        INSERT INTO `selection` (`name`, `description`, `price`, `image`, `is_deleted`, `updated_at`, `id_camp_site`)
        VALUES
        (CONCAT('Adventure Package - Site ', campsite_id), 'Full day of guided outdoor adventures customized to your preferences.', 80.00 + RAND() * 40, 'selections/adventure.jpg', FALSE, NOW(), campsite_id),
        (CONCAT('Local Experience - Site ', campsite_id), 'Cultural immersion with local communities, including traditional meal.', 45.00 + RAND() * 25, 'selections/local.jpg', FALSE, NOW(), campsite_id);

        SET campsite_id = campsite_id + 1;
    END WHILE;
END $$

DELIMITER ;

-- Then call the procedure
CALL insert_selections();


DELIMITER $$

CREATE PROCEDURE populate_camp_type_facility()
BEGIN
    DECLARE camp_type_id INT DEFAULT 1;
    DECLARE num_facilities INT;
    DECLARE facility_counter INT;
    DECLARE random_facility INT;
    DECLARE exists_count INT;

    WHILE camp_type_id <= 100 DO
        -- Always add WiFi (id_facility = 1)
        INSERT INTO camp_type_facility (id_facility, id_camp_type) 
        VALUES (1, camp_type_id);
        
        SET num_facilities = FLOOR(2 + (RAND() * 6)); -- 2 to 7
        SET facility_counter = 0;
        
        WHILE facility_counter < num_facilities DO
            SET random_facility = FLOOR(2 + (RAND() * 19)); -- id 2 to 20

            SELECT COUNT(*) INTO exists_count 
            FROM camp_type_facility 
            WHERE id_facility = random_facility AND id_camp_type = camp_type_id;

            IF exists_count = 0 THEN
                INSERT INTO camp_type_facility (id_facility, id_camp_type) 
                VALUES (random_facility, camp_type_id);
                SET facility_counter = facility_counter + 1;
            END IF;
        END WHILE;
        
        SET camp_type_id = camp_type_id + 1;
    END WHILE;
END $$

DELIMITER ;

-- Run it
CALL populate_camp_type_facility();


DELIMITER $$

CREATE PROCEDURE populate_camp_site_place_type()
BEGIN
    DECLARE campsite_id INT DEFAULT 1;
    DECLARE num_place_types INT;
    DECLARE place_type_counter INT;
    DECLARE random_place_type INT;
    DECLARE exists_count INT;

    WHILE campsite_id <= 50 DO
        SET num_place_types = FLOOR(1 + RAND() * 3); -- 1 to 3 place types
        SET place_type_counter = 0;

        WHILE place_type_counter < num_place_types DO
            SET random_place_type = FLOOR(1 + RAND() * 15); -- place type IDs 1 to 15

            SELECT COUNT(*) INTO exists_count
            FROM camp_site_place_type
            WHERE id_place_type = random_place_type AND id_camp_site = campsite_id;

            IF exists_count = 0 THEN
                INSERT INTO camp_site_place_type (id_camp_site, id_place_type)
                VALUES (campsite_id, random_place_type);
                SET place_type_counter = place_type_counter + 1;
            END IF;
        END WHILE;

        SET campsite_id = campsite_id + 1;
    END WHILE;
END $$

DELIMITER ;

-- Then run it with:
CALL populate_camp_site_place_type();

-- Associate campsites with utilities
-- Each campsite will have 3-8 utilities
DELIMITER $$

CREATE PROCEDURE populate_camp_site_utilities()
BEGIN
    DECLARE campsite_id INT DEFAULT 1;
    DECLARE num_utilities INT;
    DECLARE utility_counter INT;
    DECLARE random_utility INT;
    DECLARE exists_count INT;

    WHILE campsite_id <= 50 DO
        SET num_utilities = 3 + FLOOR(RAND() * 6); -- 3 to 8 utilities
        SET utility_counter = 0;

        WHILE utility_counter < num_utilities DO
            SET random_utility = 1 + FLOOR(RAND() * 15); -- utility IDs 1 to 15

            SELECT COUNT(*) INTO exists_count
            FROM camp_site_utility
            WHERE id_utility = random_utility AND id_camp_site = campsite_id;

            IF exists_count = 0 THEN
                INSERT INTO camp_site_utility (id_camp_site, id_utility)
                VALUES (campsite_id, random_utility);
                SET utility_counter = utility_counter + 1;
            END IF;
        END WHILE;

        SET campsite_id = campsite_id + 1;
    END WHILE;
END $$

DELIMITER ;

-- Then call the procedure:
CALL populate_camp_site_utilities();

DELIMITER $$

DELIMITER $$

CREATE PROCEDURE add_campsite_images()
BEGIN
    DECLARE campsite_id INT DEFAULT 1;
    DECLARE num_images INT;
    DECLARE image_counter INT;

    WHILE campsite_id <= 50 DO
        SET num_images = 3 + FLOOR(RAND() * 4); -- 3 to 6 images
        SET image_counter = 1;

        WHILE image_counter <= num_images DO
            INSERT INTO `camp_site_gallery` (`id_camp_site`, `path`)
            VALUES (campsite_id, CONCAT('/images/campsite_', campsite_id, '_', image_counter, '.jpg'));

            SET image_counter = image_counter + 1;
        END WHILE;

        SET campsite_id = campsite_id + 1;
    END WHILE;
END$$

DELIMITER ;

-- Call the procedure
CALL add_campsite_images();



-- Add custom prices for some camp types
-- We'll add weekend surcharges and holiday rates for some camp types
DELIMITER $$

CREATE PROCEDURE add_custom_prices()
BEGIN
    DECLARE camp_type_id INT DEFAULT 1;
    DECLARE weekend_date DATE;
    DECLARE holiday_date DATE;
    DECLARE base_price DECIMAL(10,2);
    DECLARE weekend_price DECIMAL(10,2);
    DECLARE holiday_price DECIMAL(10,2);

    WHILE camp_type_id <= 100 DO
        IF RAND() < 0.3 THEN  -- 30% chance to assign custom prices
            -- Next Saturday
            SET weekend_date = DATE_ADD(CURDATE(), INTERVAL (6 - WEEKDAY(CURDATE())) DAY);

            -- Holiday: 30 days from today
            SET holiday_date = DATE_ADD(CURDATE(), INTERVAL 30 DAY);

            -- Get base price
            SELECT price INTO base_price FROM camp_type WHERE id = camp_type_id;

            SET weekend_price = base_price * 1.25;
            SET holiday_price = base_price * 1.5;

            -- Insert custom prices
            INSERT INTO custom_price (id_camp_type, date, price)
            VALUES (camp_type_id, weekend_date, weekend_price),
                   (camp_type_id, DATE_ADD(weekend_date, INTERVAL 1 DAY), weekend_price),
                   (camp_type_id, holiday_date, holiday_price);
        END IF;

        SET camp_type_id = camp_type_id + 1;
    END WHILE;
END$$

DELIMITER ;

-- Call the procedure
CALL add_custom_prices();

INSERT INTO `report` (`id_camp_site`, `id_user`, `status`, `created_at`, `message`, `report_type`)
VALUES
(1, 3, 'Pending', DATE_SUB(NOW(), INTERVAL 5 DAY), 'The facilities were not as described. No hot water in the shower.', 'Complaint'),
(2, 4, 'Resolved', DATE_SUB(NOW(), INTERVAL 10 DAY), 'Beautiful location, but there were issues with loud noises from nearby construction.', 'Feedback'),
(3, 2, 'Denied', DATE_SUB(NOW(), INTERVAL 15 DAY), 'The site was not clean when we arrived.', 'Complaint'),
(4, 3, 'Pending', DATE_SUB(NOW(), INTERVAL 3 DAY), 'Would suggest adding more lighting around the pathways.', 'Suggestion'),
(5, 1, 'Resolved', DATE_SUB(NOW(), INTERVAL 8 DAY), 'Excellent service but would recommend adding more vegetarian food options.', 'Suggestion');

-- Add more reports for random campsites
INSERT INTO `report` (`id_camp_site`, `id_user`, `status`, `created_at`, `message`, `report_type`)
SELECT 
    FLOOR(1 + RAND() * 50) as id_camp_site,
    FLOOR(1 + RAND() * 4) as id_user,
    ELT(FLOOR(1 + RAND() * 3), 'Pending', 'Resolved', 'Denied') as status,
    DATE_SUB(NOW(), INTERVAL FLOOR(1 + RAND() * 30) DAY) as created_at,
    ELT(FLOOR(1 + RAND() * 5), 
        'The WiFi connection was very weak throughout our stay.',
        'There were issues with the water heating system.',
        'Great experience overall, but the description mentioned a view that was obstructed.',
        'The staff was incredibly helpful and went above and beyond.',
        'Would recommend adding more privacy between camping spots.') as message,
    ELT(FLOOR(1 + RAND() * 3), 'Complaint', 'Feedback', 'Suggestion') as report_type
FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) a,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3) b
LIMIT 15;

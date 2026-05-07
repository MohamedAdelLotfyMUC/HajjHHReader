-- Create Database
CREATE DATABASE IF NOT EXISTS rfid_db;
USE rfid_db;

-- Tag Reads Table
CREATE TABLE IF NOT EXISTS tag_reads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    epc VARCHAR(255) NOT NULL,
    tid VARCHAR(255),
    timestamp DATETIME(6),
    antenna_id INT,
    rssi DOUBLE,
    count INT,
    frequency_point DOUBLE,
    phase DOUBLE,
    reader_id VARCHAR(255) NOT NULL,
    user_data TEXT,
    reserved VARCHAR(255),
    raw_payload TEXT,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    INDEX idx_epc (epc),
    INDEX idx_reader_id (reader_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

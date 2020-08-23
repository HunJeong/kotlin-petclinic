CREATE TABLE IF NOT EXISTS owner_certifications (
    id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT(20) UNSIGNED NOT NULL,
    email VARCHAR(32) NOT NULL,
    password VARCHAR(128) NOT NULL,
    disabled_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
    UNIQUE(email),
    UNIQUE(owner_id),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
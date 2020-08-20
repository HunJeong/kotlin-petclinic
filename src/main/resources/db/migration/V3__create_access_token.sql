CREATE TABLE IF NOT EXISTS access_tokens (
    id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT(20) UNSIGNED NOT NULL,
    token VARCHAR(20) NOT NULL,
    expire_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE(token),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
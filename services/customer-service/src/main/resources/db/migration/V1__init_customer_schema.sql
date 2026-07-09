CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    cccd VARCHAR(50) NOT NULL,
    cccd_url VARCHAR(500),
    gplx_number VARCHAR(50),
    address VARCHAR(255),
    avatar_url VARCHAR(500),
    gplx_url VARCHAR(500),
    status VARCHAR(20) NOT NULL, -- ACTIVE, LOCKED
    role VARCHAR(20) NOT NULL, -- CUSTOMER, ADMIN
    admin_note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

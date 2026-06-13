-- Enable pgcrypto for gen_random_uuid() and encryption helpers
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Users table
CREATE TABLE users (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    google_id     VARCHAR(255) UNIQUE,
    email         VARCHAR(320),
    display_name  VARCHAR(255),
    avatar_url    VARCHAR(1024),
    is_guest      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_seen_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- OAuth tokens (app layer encrypts before insert)
CREATE TABLE oauth_tokens (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    refresh_token  TEXT NOT NULL,
    access_token   TEXT NOT NULL,
    token_type     VARCHAR(50) NOT NULL DEFAULT 'Bearer',
    scopes         TEXT,
    expiry_date    TIMESTAMPTZ NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id)
);

-- Backup receipts (Drive metadata only — never raw files)
CREATE TABLE backup_metadata (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    drive_file_id  VARCHAR(255) NOT NULL,
    file_name      VARCHAR(255) NOT NULL,
    file_size      BIGINT NOT NULL,
    checksum       VARCHAR(64) NOT NULL,
    compression    VARCHAR(20) NOT NULL DEFAULT 'GZIP',
    backed_up_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    status         VARCHAR(20) NOT NULL DEFAULT 'COMPLETED'
    -- status values: PENDING | COMPLETED | FAILED | VERIFIED
);

-- JWT refresh token sessions
CREATE TABLE user_sessions (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    refresh_token_hash  VARCHAR(64) NOT NULL UNIQUE,
    device_info         VARCHAR(255),
    ip_address          VARCHAR(45),
    issued_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at          TIMESTAMPTZ NOT NULL,
    revoked_at          TIMESTAMPTZ
);

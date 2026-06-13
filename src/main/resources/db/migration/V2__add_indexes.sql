CREATE INDEX idx_users_google_id
    ON users(google_id) WHERE google_id IS NOT NULL;
CREATE INDEX idx_backup_user_time
    ON backup_metadata(user_id, backed_up_at DESC);
CREATE INDEX idx_backup_status
    ON backup_metadata(status) WHERE status != 'COMPLETED';
CREATE INDEX idx_sessions_user
    ON user_sessions(user_id, expires_at DESC);
CREATE INDEX idx_sessions_token
    ON user_sessions(refresh_token_hash);

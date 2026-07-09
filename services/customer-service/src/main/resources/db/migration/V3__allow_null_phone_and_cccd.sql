-- Allow NULL for phone and cccd in users table since they are now optional at registration
ALTER TABLE users ALTER COLUMN phone DROP NOT NULL;
ALTER TABLE users ALTER COLUMN cccd DROP NOT NULL;

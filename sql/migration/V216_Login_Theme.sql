-- Character select theme per login (Etc.wz customLoginTheme.img id; 0 = Beneath the World Tree (Day)).
ALTER TABLE user_quick_start ADD COLUMN logintheme INT NOT NULL DEFAULT 0;

-- Inner Ability presets (v265 has 3): each line belongs to one preset (0-2); existing lines are preset 0.
ALTER TABLE characterpotentials ADD COLUMN preset INT NOT NULL DEFAULT 0;

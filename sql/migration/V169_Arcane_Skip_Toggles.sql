-- Arcane River story skip and its free Arcane Symbols: both on (the defaults); Server Control toggles them.
INSERT IGNORE INTO server_features (name, value, updated_by) VALUES
    ('arcane_skip', '1', 'migration'),
    ('arcane_skip_symbols', '1', 'migration');

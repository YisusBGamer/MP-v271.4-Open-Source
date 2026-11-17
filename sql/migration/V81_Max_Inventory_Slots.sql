UPDATE inventories SET slots = 128 WHERE type IN (1, 2, 3, 4, 5) AND (slots IS NULL OR slots < 128);

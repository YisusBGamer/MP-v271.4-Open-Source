-- V146 gave Chaos Vellum the Root Abyss (Warrior) Lucky Item
-- Scroll (2048905), but the server has no handler for Lucky Item scrolls (the client's request goes unanswered), so
-- it did nothing. It comes off the table until Lucky Item scrolls are built.
DELETE FROM mob_drops WHERE itemid = 2048905;

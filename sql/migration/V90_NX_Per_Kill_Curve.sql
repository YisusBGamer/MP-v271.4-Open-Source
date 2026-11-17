-- Monster NX now follows a level curve (1 NX at low levels up to 100 NX per kill at level 275, bosses 3x, never more
-- than 100 per kill). The old NX rate 6.25 multiplied the old, much larger amounts: it goes back to 1.
-- A rate an admin already changed from 6.25 is kept.
UPDATE server_rates SET value = 1 WHERE name = 'nx' AND value = 6.25;

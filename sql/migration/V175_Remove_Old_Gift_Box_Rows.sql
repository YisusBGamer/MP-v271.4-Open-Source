-- Gift box rows from systems that were removed: starter supplies and Sprout medal, Hot Time, onboarding checklist,
-- burning level supplies, Hyper Burning MAX gifts, Swordie Coin overflow. Admin and first-rebirth gifts stay.
-- The rows are copied to first_enter_rewards_removed first; put them back with
-- INSERT INTO first_enter_rewards SELECT * FROM first_enter_rewards_removed;
CREATE TABLE IF NOT EXISTS first_enter_rewards_removed LIKE first_enter_rewards;

INSERT IGNORE INTO first_enter_rewards_removed
SELECT * FROM first_enter_rewards
WHERE descr IN ('Starter supplies', 'Sprout medal', 'Hot Time', 'Onboarding checklist')
   OR descr LIKE 'Level % supplies'
   OR descr LIKE 'Hyper Burning MAX%'
   OR descr LIKE '% Swordie Coins (%';

DELETE FROM first_enter_rewards
WHERE descr IN ('Starter supplies', 'Sprout medal', 'Hot Time', 'Onboarding checklist')
   OR descr LIKE 'Level % supplies'
   OR descr LIKE 'Hyper Burning MAX%'
   OR descr LIKE '% Swordie Coins (%';

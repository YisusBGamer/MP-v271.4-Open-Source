-- Spell Traces filled the Etc tab: back to normal amounts, and monsters drop them far less often.
-- The Spell Trace rate multiplied every drop's amount (15x); 1 = the drop tables' own amounts.
update server_rates set value = 1 where name = 'spell_trace';
-- 72 monsters dropped them every kill (10000); 50 is 0.5%, about 10% a kill at the 20x drop rate.
update mob_drops set chance = 50 where itemid = 4001832 and chance > 50;

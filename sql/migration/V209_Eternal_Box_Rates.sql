-- Eternal Armor Box rates from the Grandis treasure chests: 10% on Chaos/Hard, 20% on Extreme (chance is per 10,000)
UPDATE mob_drops SET chance = 1000 WHERE itemid IN (2638064, 2638065, 2830306, 2638066, 2639253) AND chance = 100;
UPDATE mob_drops SET chance = 2000 WHERE itemid IN (2638064, 2638065, 2830306) AND chance = 200;

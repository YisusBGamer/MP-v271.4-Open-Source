-- Eternal set sources (maplestorywiki.net Eternal_Set and the bosses' /Monster pages, GMS Interactive amounts): the
-- Grandis treasure chests drop the v265 tokens (10 for an Eternal piece; fragments 2 for a token) and armor boxes
-- (one piece). The wiki gives no box rates: 1% on Chaos/Hard, 2% on Extreme. Opened by scripts/item/eternal_source.py.

-- Kalos the Guardian: Normal Fragments, Chaos/Extreme Residual Determinations, Divine Eternal Armor Box (Chaos, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880808, 2636606, 10000, 3, 3 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880808 AND itemid = 2636606);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2634472, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2634472);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881038, 2638064, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881038 AND itemid = 2638064);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2634472, 10000, 14, 14 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2634472);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881058, 2638064, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881058 AND itemid = 2638064);

-- Kaling: Easy/Normal Ring Fragments, Hard/Extreme Ferocious Beast Entanglement Rings, Ferocious Beast Eternal Armor Box (Hard, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880915, 2636607, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880915 AND itemid = 2636607);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880845, 2636607, 10000, 5, 5 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880845 AND itemid = 2636607);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2635747, 10000, 7, 7 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2635747);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880945, 2638065, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880945 AND itemid = 2638065);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2635747, 10000, 18, 18 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2635747);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8880975, 2638065, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8880975 AND itemid = 2638065);

-- First Adversary (World Heart): Normal Whispers, Hard/Extreme Echoes of Ancient Resolve, Ancient Eternal Armor Box (Hard, Extreme)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881745, 2830305, 10000, 4, 4 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881745 AND itemid = 2830305);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2830304, 10000, 6, 6 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2830304);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881775, 2830306, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881775 AND itemid = 2830306);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2830304, 10000, 16, 16 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2830304);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881805, 2830306, 200, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881805 AND itemid = 2830306);

-- Limbo (Paradoxical Truth): Distorted Ambition, Eternal Armor of Desire Box (Hard)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881334, 2638063, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881334 AND itemid = 2638063);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2638063, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2638063);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881384, 2638066, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881384 AND itemid = 2638066);

-- Baldrix (Concentrated Magical Essence): Trace of Eternal Loyalty, Eternal Armor of Oaths Box (Hard)
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881624, 2639252, 10000, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881624 AND itemid = 2639252);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2639252, 10000, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2639252);
INSERT INTO mob_drops (mobid, itemid, chance, minquant, maxquant) SELECT 8881674, 2639253, 100, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM mob_drops WHERE mobid = 8881674 AND itemid = 2639253);

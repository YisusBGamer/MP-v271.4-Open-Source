# Shadow of Chaos quest boss map (Hayato 57149, Kanna 57445). Kanna's copy of the quest is accepted too.
if (sm.hasQuest(57149) or sm.hasQuest(57445)) and not sm.hasMobsInField():
    sm.spawnMob(9421568, 379, 39, False)

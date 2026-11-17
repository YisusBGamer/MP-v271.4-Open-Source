# 410004001 (Narin : Dalbourde Studio) - Lara's Narin story: Dark Hannah (The Ringing Bell 36235), in your copy of the
# studio
if sm.hasQuest(36235) and not sm.isComplete(36235) and chr.getInstance() is not None and not sm.hasMobsInField():
    sm.spawnMob(8645229, 400, 248, False)
    sm.chatScript("Hannah, swallowed by darkness, attacks!")

# Tyrant's Castle 3rd Floor (993167660), own copy: [Heliseum] Velderoth the Traitor (38857). Velderoth waits in the
# middle of the floor while the quest still needs him beaten (q38857s brings you in).
VELDEROTH = 9834323
QUEST = 38857

if chr.getInstance() is not None and sm.hasQuest(QUEST) and not sm.isComplete(QUEST) and not sm.hasMobsInField():
    sm.spawnMob(VELDEROTH, -4969, 152, False)
    sm.chatScript("Get Velderoth out of the way!")

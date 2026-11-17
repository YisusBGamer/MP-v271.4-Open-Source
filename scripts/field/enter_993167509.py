# Tyrant's Throne (993167509), own copy: [Heliseum] The Final Battle (38858). The story Magnus (9834324) waits here
# while the quest still needs him beaten (q38858s brings you in).
MAGNUS = 9834324
QUEST = 38858

if chr.getInstance() is not None and sm.hasQuest(QUEST) and not sm.isComplete(QUEST) and not sm.hasMobsInField():
    sm.spawnMob(MAGNUS, 602, -1391, False)
    sm.chatScript("Defeat Magnus!")

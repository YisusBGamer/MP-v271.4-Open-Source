# In Phantom's Name (41062) handed in to Shati in Mirror World Ariant: the Sand Bandits' letter (41063) turns up in town.
if sm.getFieldID() == 322000000 and chr.getLevel() >= 115 and not sm.hasQuest(41063) \
        and not sm.hasQuestCompleted(41063):
    sm.spawnNpcIfAbsent(2420021, 1600, 271)

# id 301070010 (Crimsonheart Castle : Path to the Altar), field 301070010
# Crimsonheart Castle (Masteria story, quests 31240-31261): Master Red Nirg (8148012) appears for Outside the Castle
# (31258). The quest is started only once (starting it again would reset it on every visit).
if not sm.hasQuest(31258) and not sm.hasQuestCompleted(31258):
    sm.startQuest(31258)
if sm.hasQuest(31258) and not sm.hasMobsInField(sm.getFieldID(), 8148012):
    sm.spawnMob(8148012, 700, 323, False)

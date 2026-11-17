# [Tallahart] The Decisive Battle (36994): Baldrix wants it one-on-one. The fight is in an own copy of the Phantasmal
# Night Sanctum where Baldrix stands (993263080, Map.wz life); its clear script ends the quest.
SPEAKER = 3006833
BALDRIX_FIGHT = 993263080

sm.setSpeakerID(SPEAKER)
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.sendAskAccept("It's time to face #rBaldrix#k. Trust yourself, and fight in Luce's honor."):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warpInstanceIn(BALDRIX_FIGHT, 0)
sm.dispose()

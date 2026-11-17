# Proteggtion (1731), start - Spiegelmann at the Hatchery (Picture World). The client has no start lines; written
# from the quest's own Quest.wz summary ("Hobi's keep trying to steal the egg! You must protect it!"). His official
# lines at the end follow.
SPIEGELMANN, HOBI = 9074300, 9810301
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(SPIEGELMANN)
        if sm.sendAskAccept("Shh! Do you hear that? The Hobis are back - they keep trying to steal Manon's Egg! You have to protect it!"):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID) and not sm.isComplete(parentID) and sm.getFieldID() == 956030000 \
            and not sm.hasMobsInField(956030000, HOBI):
        sm.spawnMob(HOBI, -300, 188, False)
        sm.spawnMob(HOBI, 200, 188, False)
sm.dispose()

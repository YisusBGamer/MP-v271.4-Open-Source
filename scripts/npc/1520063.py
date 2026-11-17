# Sugar (1520063), Six Path Crossway. Explorer story: At a Crossroads (32241) and Sugar and the Great Tree (32242) are
# Sugar's quests under another NPC id (1520000) that isn't placed anywhere, so she handles them here; the Tree Bugs are
# fought in the story copy of the Six Path Crossway (910400200, field/sixroad_adventure).
if sm.hasQuest(32241):
    sm.questEndDialogue(32241, 1520063)
elif sm.hasQuestCompleted(32241) and not sm.hasHadQuest(32242):
    if sm.questStartDialogue(32242, 1520063):
        sm.warpInstanceIn(910400200, 0)
elif sm.hasQuest(32242) and not sm.isComplete(32242):
    sm.warpInstanceIn(910400200, 0)
else:
    sm.setSpeakerID(1520063)
    sm.sendSayOkay("Bugs aren't my only weakness. I also get lost all the time.")
sm.dispose()

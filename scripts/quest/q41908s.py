# New Changes (41908), start (offered by the quest notifier after Relic World 2) - Lapis (2400009), the sword's spirit.
# The quest asks for one Scarecrow (9300809) and Lapis's own line is "I spent all my remaining energy to summon these
# monsters! Do your best to find their differences!" The Scarecrows are summoned next to you once the quest is on
# (again if you come back to it without one around).
SCARECROW = 9300809
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.questStartDialogue(parentID, 2400009)
    if sm.hasQuest(parentID) and not sm.isComplete(parentID) and not sm.hasMobsInField(sm.getFieldID(), SCARECROW):
        pos = chr.getPosition()
        for dx in (-150, 150):
            sm.spawnMob(SCARECROW, pos.getX() + dx, pos.getY(), False)
        sm.setSpeakerID(2400009)
        sm.sendSayOkay("Hmm? I spent all my remaining energy to summon these monsters!\r\nDo your best to find their "
                       "differences!")
sm.dispose()

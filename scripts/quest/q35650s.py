# [Genesis Weapon] Trailing the Traces of the Black Mage (35650): the line's tracker. Its start NPC stands nowhere,
# so field/enter_993060090 normally starts it; called while it runs, offers the trip to the Pavilion.
PAVILION = 993060090

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if chr.getLevel() >= 255 and sm.hasQuestCompleted(35815):
        sm.startQuestNoCheck(parentID)
elif sm.hasQuest(parentID) and sm.getFieldID() != PAVILION:
    sm.setSpeakerID(9063150)
    if sm.sendAskYesNo("Shall I take you to the #bEvening Conference Pavilion#k?"):
        sm.warp(PAVILION, 0)
sm.dispose()

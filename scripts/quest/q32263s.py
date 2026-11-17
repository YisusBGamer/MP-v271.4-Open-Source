# Following the Bowman (32263) - Tess at the Six Path Crossway
# Starts at the Six Path Crossway, where Tess (field/explorationPoint.py) is heading for the Nautilus. Following him
# into Nautilus Harbor leads to the Way to the Beach, where he meets the rabbit; field/explorer_way_to_beach.py sets
# the quest's own record (32263 = "1") that Tess's hand-in waits for.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.getFieldID() != 104020000:
        sm.setPlayerAsSpeaker()
        sm.sendSayOkay("Who's Tess and where might I find him? I should head back to the #b#m104020000##k. That hasn't failed me yet!")
    else:
        sm.questStartDialogue(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(parentID) != "1":
    sm.chatScript("Tess is heading for the Nautilus. Follow him to Nautilus Harbor!")
sm.dispose()

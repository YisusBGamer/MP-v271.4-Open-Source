# [Fox Valley] A Foxy Disguise (34747) - Maru
# The start script is missing (no Say.img lines either). Maru hands over the Lame Faux Fox Ears the quest asks for;
# the quest itself says to give up the quest and talk to him again for another pair. Written from the quest's own
# summaries.
MARU = 3002021
EARS = 1005059  # Lame Faux Fox Ears

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MARU)
    if not sm.canHold(EARS):
        sm.sendSayOkay("Make some room in your Equip inventory first.")
    elif sm.sendAskAccept("Behold! A fine and furry set of ears, made by yours truly, the Master Crafter! "
                          "Put them on and you'll look just like a fox. Tell me when you're ready to carry out "
                          "#bOperation Powder Puff#k!"):
        sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(EARS):
            sm.giveItem(EARS)
sm.dispose()

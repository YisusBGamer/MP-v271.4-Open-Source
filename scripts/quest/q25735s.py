# Evil Auras! (25735, Kaiser)
# Tracks the evil aura to the Nefarious Priests at the Pantheon Vacant Lot (940000000, field/event_25735.py). No
# Say.img lines exist for the start; written from the quest's own summary.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("An evil aura... right here, near Pantheon. I have to find where it's coming from.\r\n"
                        "#r(If you fail the quest, you can forfeit it and start again.)#k"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(25802) != "1":
    sm.warp(940000000, 0)
sm.dispose()

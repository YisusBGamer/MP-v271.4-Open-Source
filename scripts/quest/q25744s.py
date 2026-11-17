# Reclaim the Stolen Relic! (25744, Kaiser)
# Tracks the evil aura to the Nefarious Priests near the West Sanctum (940000070, field/event_25744.py). No Say.img
# lines exist for the start; written from the quest's own summary.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("The priests and the Relic are gone... I'll focus my mind and track down their evil aura.\r\n"
                        "#b#e(If you fail the quest, you can forfeit it and start again.)#n#k"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(25805) != "1":
    sm.warp(940000070, 0)
sm.dispose()

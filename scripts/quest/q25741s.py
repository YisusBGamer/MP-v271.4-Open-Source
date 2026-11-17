# South Sanctum Duty (25741, Kaiser)
# The evil aura again, at the back of the South Sanctum (940000040, field/event_25741.py). No Say.img lines exist for
# the start; written from the quest's own summary.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("That evil aura again! The Nefarious Priests must be nearby!\r\n"
                        "#b#e(If you fail the quest, you can forfeit it and start it again.)#n#k"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(25803) != "1":
    sm.warp(940000040, 0)
sm.dispose()

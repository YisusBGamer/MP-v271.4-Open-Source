# [Arteria] Wrath (38651): the fight with the Apostle Reina on the Arteria's bridge, in an own copy of the Command Room
# (993217800, field/enter_993217800). Beating her sets the quest's "dir" record and ends it.
COMMAND_ROOM_FIGHT = 993217800

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("(There is only one thing left to do: defeat the Apostle Reina.)"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(parentID, "dir") != "1":
    sm.warpInstanceIn(COMMAND_ROOM_FIGHT, 0)
sm.dispose()

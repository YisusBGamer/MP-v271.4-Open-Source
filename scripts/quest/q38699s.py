# [Carcion] Last Chance (38699): Limbo took Keira's power and fled to the Sacred Tree Summit. The fight is in an own
# copy of the summit (993217874, field/enter_993217874) with the story Limbo; beating him sets the quest's
# "boss=clear" record and ends it.
KEIRA = 3006542
SUMMIT_FIGHT = 993217874

sm.setSpeakerID(KEIRA)
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.sendAskAccept("Limbo has taken my power and gone to the #bSacred Tree Summit#k. This is our last chance. "
                        "After him!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and sm.getQRValue(parentID, "boss") != "clear":
    sm.warpInstanceIn(SUMMIT_FIGHT, 0)
sm.dispose()

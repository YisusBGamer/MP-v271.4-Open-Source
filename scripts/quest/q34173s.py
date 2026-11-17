# [Chu Chu] Below the Falls (34173): Rona points the way to Chu Chu Island. Nobody hands it in, so it's done once taken.
RONA = 3003104

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(RONA)
    sm.sendNext("The Arcane River keeps flowing beneath the Vanishing Journey, towards the Black Mage.")
    sm.sendNext("Jump below #bDamp Falls#k and follow the river. Whatever lies further down, you'll find it there.")
    sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.completeQuest(parentID)
    sm.chatScript("Jump below Damp Falls and follow the Arcane River towards the Black Mage.")
sm.dispose()

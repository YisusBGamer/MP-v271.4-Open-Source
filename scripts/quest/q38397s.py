# 38397 [Shangri-La] Collapsing Shangri-La 3 - start (Shuli, Orchid Leaf-Green Summer). Hundun (8880851) appears in
# Chrysanthemum-Blooming Autumn, where Master Tai Yu waits; it spawns nowhere in the data, so the fight is in a
# private copy of Autumn. Summer's right portal (portal/pt_410007023) starts it again if you leave.
SHULI = 3006166
HUNDUN = 8880851
AUTUMN = 410007023

sm.setSpeakerID(SHULI)
sm.setBoxChat()
sm.sendNext("Ugh... my head. Taowu... is it gone? Thank you, #h0#.")
sm.sendNext("But it's not over. I can feel the autumn barrier breaking. Another of the Four Perils is there... "
            "#rHundun#k.")
if sm.sendAskAccept("Master Tai Yu went ahead to #b#m410007023##k. Please, hurry to him!"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID) and not sm.isComplete(parentID):
        sm.warpInstanceIn(AUTUMN, 1)
        sm.setInstanceTime(1800, AUTUMN)
        sm.spawnMob(HUNDUN, 300, 64, False)
        sm.chatScript("Hundun, one of the Four Perils, appears! Defeat it.")
sm.dispose()

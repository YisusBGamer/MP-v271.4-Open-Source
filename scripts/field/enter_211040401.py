# 211040401 (Hidden Street : Holy Ground at the Snowfield) - Explorer story: the Master of Disguise, posing as Olive
# (Cornering the Master of Disguise 32294); he drops the Gem of Nihal Desert (V96).
# Hearing Voices (32293) asks to get here through the obelisk's hidden portal in Sharp Cliff I: arriving with the
# quest still open sets its record (32293 = "1") and finishes it (it has no NPC to hand it in to).
if sm.hasQuest(32293) and sm.getQRValue(32293) != "1":
    sm.setQRValue(32293, "1")
    sm.completeQuest(32293)
if sm.hasQuest(32294) and not sm.isComplete(32294) and not sm.hasMobsInField():
    sm.spawnMob(9300804, 200, 454, False)
    sm.chatScript("The Master of Disguise drops his act!")
sm.dispose()

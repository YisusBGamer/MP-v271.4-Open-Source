# Burning Maple Tree Hill 4 (993166023), own copy: Battle Against the Black Shadow (36322). Winning sets the quest's
# kill flag and finishes it, starts The Miracle of the Maple Leaf (36323) and brings out the Maple Tree
# (npc/Mapletree_click.py). Dying lands on Maple Tree Hill (110000); click the Black Shadow there to retry.
BLACK_SHADOW = 8645262
MAPLE_TREE = 1541068
QUEST = 36322

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "kill") != "1":
    if not sm.hasMobById(BLACK_SHADOW):
        sm.spawnMob(BLACK_SHADOW, 300, 425, False)
    sm.chatScript("Defeat the Black Shadow!")
    sm.waitForMobDeath(BLACK_SHADOW)
    sm.setQRValue(QUEST, "kill", "1")
    sm.completeQuest(QUEST)
    sm.setQRValue(36300, "84", "h0")
    sm.chatScript("You defeated the Black Shadow! Check on Sugar and the Maple Tree.")
    if not sm.hasQuest(36323) and not sm.hasQuestCompleted(36323):
        sm.startQuest(36323)
    sm.spawnNpc(MAPLE_TREE, -551, 139)

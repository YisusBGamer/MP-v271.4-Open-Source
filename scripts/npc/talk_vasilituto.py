# Vasily (10305) | Maple Road : Port
# v265 blocks the Maple Road quests (32211-32216), so the Sangri-La sails without them. An Explorer or Dual Blade
# beginner boarding has Maple Island's part of the start (36301-36304) done here and boards as Victoria Island or
# Bust (36306): Sugar (The Adventure Begins, 36307) and the job instructors are waiting in Lith Harbor.
ISLAND = (36301, 36302, 36303, 36304)
BOARDING = 36306
LITH_HARBOR = 104000000

sm.setSpeakerID(parentID)
if sm.hasQuest(32214):
    if sm.sendAskYesNo("I'll let you on board. Go defeat the monsters rampaging my ship."):
        sm.warpInstanceIn(4000033, 0, False)
elif sm.sendAskYesNo("The Sangri-La is ready to sail for #bLith Harbor#k on #bVictoria Island#k. All aboard?"):
    if chr.getJob() == 0 and chr.getSubJob() in (0, 1) and not sm.hasQuestCompleted(BOARDING):
        for q in ISLAND:
            if not sm.hasQuestCompleted(q):
                sm.completeQuestNoRewards(q)
        sm.completeQuestNoRewards(BOARDING)
    sm.warp(LITH_HARBOR, 0)
else:
    sm.sendSayOkay("Come back when you're ready to set sail.")
sm.dispose()

# 915020200 -> 915020201 Leafre Treasure Vault ("An Expensive Acquisition", 25121: defeat the Guardioso)
JOB_QUEST = 25121
GUARDIOSO_NPC = 2159462
GUARDIOSO_MOB = 9001047

if 2500 <= chr.getJob() <= 2512:
    # Shade: "despite the Guardioso, you were able to get into the storage room pretty easily"
    if sm.hasQuest(38054) or sm.hasQuestCompleted(38053) and not sm.hasQuestCompleted(38054):
        sm.warp(915020201, 2)
    else:
        sm.chat("The Guardioso is watching. Better leave it alone.")
elif chr.getJob() not in (2411, 2412) or chr.getLevel() < 100:
    sm.chat("This location is only available for Phantoms looking to get stronger.")
elif sm.hasQuestCompleted(JOB_QUEST):
    sm.warp(915020201, 2)
elif not sm.hasQuest(JOB_QUEST):
    if sm.hasQuest(25120):
        sm.chat("Complete 'What's in a Phantom?' in your quest window and accept 'An Expensive Acquisition' first.")
    else:
        sm.chat("The Guardioso blocks the way.")
elif sm.completeQuest(JOB_QUEST):
    # the Guardioso was defeated (the quest's mob requirement)
    sm.warp(915020201, 2)
elif sm.hasMobsInField():
    sm.chat("You must defeat the Guardioso before you can enter.")
else:
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay("I don't like the way that statue looks...")
    sm.removeNpc(GUARDIOSO_NPC)
    sm.spawnMob(GUARDIOSO_MOB, 290, 180, False)
sm.dispose()

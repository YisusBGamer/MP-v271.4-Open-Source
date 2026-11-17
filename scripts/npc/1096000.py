# Skipper (1096000), Somewhere in Coco Island (3000600): "The Adventure Begins" (2573) for a new Cannoneer.
# 2573 needs v265's Maple Island quests; a Cannoneer never sees the island, so they are marked done here too.
SKIPPER = 1096000
QUEST = 2573
MAPLE_ISLAND = (36301, 36302, 36303, 36304, 36306, 36307)

sm.setSpeakerID(SKIPPER)
if sm.getChr().getJob() == 0 and not sm.hasQuestCompleted(QUEST) and not sm.hasQuest(QUEST):
    for q in MAPLE_ISLAND:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.sendNext("Greetings! Isn't this just the perfect weather for a journey? I'm Skipper, the captain of this fine ship. You must be a new Explorer, eh? Nice to meet you.")
    if sm.sendAskAccept("We're not quite ready to leave, so feel free to look around the ship while we're waiting."):
        sm.sendNext("Looks like we're all set! I think this is going to be a great voyage. Let's get underway.")
        sm.startQuestNoCheck(QUEST)
        sm.warp(3000000, 0)
    else:
        sm.sendNext("Hey, take it easy! Sometimes you just gotta wait.")
else:
    sm.sendSayOkay("Smooth sailing ahead, I can feel it in my bones.")
sm.dispose()

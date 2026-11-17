# Portal script check_xn.
# Road to the Mine 1: the way into Gelimer's closed lab, for the Xenon story.
# Clash of the Xenoroids (23710): the Silo, where Beryl waits (your own copy); after the fight, the Silo with Beryl.
# Returning to the Beginning through Data Mining 3 (23690-23695, 23721): the Forgotten Laboratory.
LAB_QUESTS = (23691, 23692, 23693, 23694, 23695, 23721)
if sm.hasQuest(23710):
    if sm.getQRValue(23723) == "1":
        sm.warp(931060041, 1)
    else:
        sm.warpInstanceIn(931060040, 1)
elif any(sm.hasQuest(q) for q in LAB_QUESTS) or (sm.hasQuestCompleted(23690) and not sm.hasQuestCompleted(23695)):
    sm.warp(931060051, 2)
else:
    chr.chatMessage("The lab's entrance is sealed.")
sm.dispose()

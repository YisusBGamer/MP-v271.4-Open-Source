SHIBA = 1057000
RYDEN = 1057001

sm.setSpeakerID(SHIBA)
sm.sendNext("Slow. Much, much too slow. Like a wilting leaf... Are you sure you have what it takes to learn to dual-wield?")
sm.sendSay("My insults slide like rain off your back... Hey, Ryden! What's with the new recruit?")
sm.completeQuest(parentID)
sm.giveExp(50)
sm.startQuestNoCheck(2604)
sm.warp(103050101)

# v265's Maple Island quests open the job and Explorer story quests after them; this tutorial stands in for them
for q in (36301, 36302, 36303, 36304, 36306, 36307):
    if not sm.hasQuestCompleted(q):
        sm.completeQuestNoRewards(q)

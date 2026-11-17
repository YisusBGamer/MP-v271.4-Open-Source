# Portal script pAqupondo.
# Canal 5 (Commerci): the barrier at the west end opens onto Canal 6 once you're investigating it
# ([Commerci Republic] Barrier to the Canals, 17700, and the quests after it).
if sm.hasQuest(17700) or sm.hasQuestCompleted(17700):
    sm.warp(865020500, 3)
else:
    chr.chatMessage("A strange barrier blocks the way.")
sm.dispose()

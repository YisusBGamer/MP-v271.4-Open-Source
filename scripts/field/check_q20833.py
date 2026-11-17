# 130030104 (Ereve garden), onUserEnter "check_q20833"  |  Cygnus tutorial
# Arriving in the garden completes "Tiny Bird" (20833), which quest 20834 "The Tranquil Garden" needs. The bird's
# start script (q20833s) records "gardenIn" and warps here.
if sm.hasQuest(20833) and not sm.hasQuestCompleted(20833):
    sm.completeQuestNoRewards(20833)

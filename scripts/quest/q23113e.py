# 23113 - Surl's Request - Transformation and Camouflage
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Claudine (2151003)

sm.setSpeakerID(2151003)  # Claudine
sm.sendNext("We have plenty of strands of #p2152010#'s hair at the Underground Base, so making the potion should be simple. Give me a moment.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This is the transformation potion that will transform you into #p2152010#. #bDrink it and then go to Watchman #p2152016# while pretending to be #p2152010##k. Make sure you fool him.")

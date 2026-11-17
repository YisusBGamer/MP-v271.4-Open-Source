# 1837 - [Selectable Program] Monster Enhancement
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075207)

sm.setSpeakerID(9075207)  # ESS
sm.sendNext("Did you defeat 20 #o9306206# and 20 #o9306207# monsters?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The monster enhancement model has ended. You'll see noticeable results by using the Evolution System.")

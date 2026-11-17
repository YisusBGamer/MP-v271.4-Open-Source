# 22407 - Making a Bigger Saddle
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kenta (2060005)

sm.setSpeakerID(2060005)  # Kenta
sm.sendNext("You brought all the materials! I'll start making your saddle right away.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("A beautiful saddle has been completed that is flexible enough to let the animal move yet strong enough to protect the rider. ")
    sm.sendSay("However, there is a limit to the flexibility of this saddle. I'm not sure how big this Dragon will get, but if it gets too big, the saddle might not be able to handle it. If that happens, you'll have to come back and order a new one.")

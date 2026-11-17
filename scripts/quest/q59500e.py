# 59500 - [Spring Valley] Teaching Fear - Alpine Goats
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Navy Tail (9400003)

sm.setSpeakerID(9400003)  # Navy Tail
sm.sendNext("You did it! I'm so relieved.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Good, good. The creatures now flee when they hear so much as the flap of a butterfly wing.")
    sm.sendSay("Don't be sad we can't approach the creatures anymore... This is MUCH better than finding out later that the animals were hurt by some heartless hunter!")

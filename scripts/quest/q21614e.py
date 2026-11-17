# 21614 - A Wolf's Heart
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Head Wolf (1202008)

sm.setSpeakerID(1202008)  # Head Wolf
sm.sendNext("Did you bring back the #t4032334#? Then let's have the kid drink it. Hand me the #t4032334# immediately.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("#t1902017# has now become an an adult. You are a great person for making #t4032334# for the kid. Please continue to take good care of #t1902017#.")

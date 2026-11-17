# 31402 - Specters Attack
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
sm.sendNext("Whew, that was a lot of Specters. But I could keep beating on them for days...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext(u"Right now, I\u2019ve gotta get to #p3001001# and #p3001000#.")

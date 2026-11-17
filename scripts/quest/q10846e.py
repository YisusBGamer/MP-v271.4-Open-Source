# 10846 - Ratz Elimination
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("How's my Storage Room? And my things? Are they all there?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You've caught all 10 Ratz! Good work! My treasures in the Storage Room are safe now.")

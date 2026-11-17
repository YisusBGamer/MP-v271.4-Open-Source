# 10832 - Precious Medicine
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("Were you able to find #t4032804#? To be honest, I wasn't expecting you to succeed, because it's so rare...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("How in the world did you find something so rare? You're better than I expected!")

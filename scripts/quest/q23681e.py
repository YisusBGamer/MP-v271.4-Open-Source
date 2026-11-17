# 23681 - A Gift Most Manly 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Professor Dreamboat (2300001)

sm.setSpeakerID(2300001)  # Professor Dreamboat
if sm.sendNext("Ah, is that #t4033644#.\r\n#L0##bIt was on top of the pile... Is it good?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I have no idea. Reading gives me hives.\r\n#L0##bOh, then how did you get to be a professor?#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("People just naturally do whatever I say. I'm very smart.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This book has a nice cover and soft edges, I'm sure your friend will enjoy it.")

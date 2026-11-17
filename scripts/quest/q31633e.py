# 31633 - [Communication] Not so Simple
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tonero (3001030)

sm.setSpeakerID(3001030)  # Tonero
sm.sendNext("Did you get everything?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext(u"Not bad, you can take some of that #rmerchant crew gold#k I gave you and get something from anybody in the Grandis Shadow. I\u2019ll let you know when I have more work.")

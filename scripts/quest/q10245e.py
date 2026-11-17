# 10245 - Lisa's Spring Flower Research 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lisa (2012012)

sm.setSpeakerID(2012012)  # Lisa
sm.sendNext("Spring flowers make me feel warm and fragrant. Do you have some Azaleas for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Azaleas mean 'love'. I hope this pink azalea will spread love around Maple World! I will give you an #bAzalea Sticker#k to always keep a loving mind.\r\n")

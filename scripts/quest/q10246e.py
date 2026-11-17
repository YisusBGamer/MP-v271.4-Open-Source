# 10246 - Lisa's Spring Flower Research 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lisa (2012012)

sm.setSpeakerID(2012012)  # Lisa
sm.sendNext("Spring flowers make me feel warm and fragrant. Do you have some Forsythias for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you! Forsythias mean 'hope'. I wish this yellow forsythia will spread hope around Maple World! I will give you a #bForsythia Sticker#k to always keep a hopeful mind.")

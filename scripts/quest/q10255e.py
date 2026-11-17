# 10255 - Chief Tatamo's Spring Flower Research 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Chief Tatamo (2081000)

sm.setSpeakerID(2081000)  # Chief Tatamo
sm.sendNext("Finally, spring is coming to Maple World. Shiny days... I am researching spring flowers now so would you like to give me your Forsythias?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you so much! Hahaha. Please come and visit me anytime you want. You are a warm-hearted person.")
    sm.sendSay("Forsythias mean 'hope'. I wish this yellow forsythia will spread hope around Maple World! I will give you a #bForsythia Sticker#k to always keep a hopeful mind.")

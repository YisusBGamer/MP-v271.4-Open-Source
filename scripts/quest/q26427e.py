# 26427 - [Maple Rewards] Saving Up 1 Reward Point at a time! 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
sm.sendNext("Oh, did the Reward Bundle open up? Then come here and grab fresh new Reward Points, just for you! Hurry up!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You earned #b#i2431872# #t2431872#s#k! \r\n\r\nLet's see how many Reward Points you have... Wow, you're already up to 100!")
    sm.sendSay("You got 100 Reward Points for just waiting 30 minutes? Fantastic! It must really like you, #b#h0##k! Congratulations.")

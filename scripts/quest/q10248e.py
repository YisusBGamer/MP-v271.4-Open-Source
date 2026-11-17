# 10248 - The Rememberer's Spring Flower Research 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: The Rememberer (1061011)

sm.setSpeakerID(1061011)  # The Rememberer
sm.sendNext("I have a lot of work to do... so I can't go find spring flowers now...  Oh! Did you bring some Azaleas for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you for bringing me Azaleas! I couldn't collect them by myself because I have a lot of work to do... anyway thank you so much!")
    sm.sendSay("Azaleas mean 'love'. I hope this pink azalea will spread love around Maple World! I will give you an #bAzalea Sticker#k to always keep a loving mind.\r\n")

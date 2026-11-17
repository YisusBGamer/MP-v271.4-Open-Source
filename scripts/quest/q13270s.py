# 13270 - [PC?] PC??? ??????!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
if sm.sendAskAccept("#b#h0##k! I'm #rchecking the attendance#k of those that are playing from PC Cafes. How about you, are you playing from one?\r\nThere are some great prizes you know."):
    sm.startQuest(parentID)
    sm.sendNext("Thanks for participating in the #bPC Cafe participation check#k!\r\nEnjoy your Mapling.")
else:
    sm.sendNext("Oh, that's a shame.\r\nCome back again if you change your mind!")

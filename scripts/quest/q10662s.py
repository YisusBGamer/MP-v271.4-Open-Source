# 10662 - [PC?] ???? ??
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
if sm.sendAskAccept("Hi #e#b#h0##k#n,\r\n#h0#, you have logged in from the #e#rPremium PC Cafe#k#n. I am giving out a very special gift #b#t2430686##k to people who login from the Premium PC Cafes just once a day.\r\nShhh, it's a secret what's in the box! Do you want to receive your gift box now and check what's in it?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Once a day is your only chance to receive a gift!")

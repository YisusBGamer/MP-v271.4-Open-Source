# 30027 - [Root Abyss] Gnarled Wooden Key
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Havis (1064031)

sm.setSpeakerID(1064031)  # Havis
sm.sendNext("Hey! I just received a request from Ereve to give you 5 #b#i4033611##t4033611##k items. You're aware that you're going to need these keys to get to where the Lutavian seal guardians are, right? You want them?")
if sm.sendAskAccept("One...two...three...four...and five! Remember, these are made with #rmagical powers#k which will fade after #rone day#k. The boss in Ereve told me to give you 5 of these every day, so come find me okay? Good luck! "):
    sm.startQuest(parentID)
else:
    sm.sendNext("You don't need them? Okay, suit yourself! ")

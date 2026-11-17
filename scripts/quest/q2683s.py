# 2683 - Henesys, Here I Come!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kyrin (1090000)

sm.setSpeakerID(1090000)  # Kyrin
sm.sendNext("Hm... I know just the person you need to see. ")
if sm.sendAskAccept("Athena Pierce is the Bowman Instructor in Henesys. She's admired by all for her selfless ways and her wisdom. Heck, she's helped me out of a jam more than once. She'll do right by you.\r\nGood luck out there, yeah?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Maybe you just need to take a deep breath first.")

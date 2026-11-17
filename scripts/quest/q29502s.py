# 29502 - Pink Bean Slayer
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Dalair (9000040)

sm.setSpeakerID(9000040)  # Dalair
if sm.sendAskAccept("#i1140003##e#b#t1140003##k\r\n\r\n#nRaise Popularity by 20,000 \r\n\r\n\r\nDo you want to test your skills to see if you're worthy of this title?"):
    sm.startQuest(parentID)
    sm.sendNext("I wish you the best.  There isn't a specific time limit, so whenever you think you're ready, come back and see me.")
else:
    sm.sendNext("Come back when you're ready.")

# 28582 - [Legends] Invitation to the Legends Awards
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
if sm.sendAskAccept("Hiya! Are you here to try out for the Legends Awards, MapleStory's greatest year-end event? The Legends Awards are to recognize all the great things adventurers and heroes like you do to bring peace to Maple World. Join in for a chance at prizes and fame!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Wait! Did I mention the fame and prizes?")

# 31100 - [Henesys Ruins] The Rumor
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9010010)

sm.setSpeakerID(9010010)  # Cassandra
if sm.sendAskAccept("So you heard the #rrumor#k too.\r\nAll I know is that the Black Mage attacked the Temple of Time.\r\nDo you want to know more?"):
    sm.startQuest(parentID)
    sm.sendNext("Then go find out for yourself! The #p2140000# of the Temple of Time should know the details.")
else:
    sm.sendNext("It seems you haven't realized the importance of this matter. Please give it another thought.")

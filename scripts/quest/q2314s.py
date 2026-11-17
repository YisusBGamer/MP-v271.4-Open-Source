# 2314 - Exploring Mushroom Forest (1)
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Secretary of Domestic Affairs (1300003)

sm.setSpeakerID(1300003)  # Secretary of Domestic Affairs
if sm.sendAskAccept("In order to rescue the princess, you must first get through Mushroom Forest. King Pepe set up a powerful barrier to block anyone from entering the castle. Please investigate this matter for us."):
    sm.startQuest(parentID)
    sm.sendNext("The Ward of Mushroom Forest can be found in #b#m106020300##k, just east of here. But be careful, that place can get pretty hairy.")
else:
    sm.sendNext("Please do not give up on the Mushking Empire!")

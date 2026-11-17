# 11448 - [Legends] Experience the Legends Awards!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #h0#! Are you enjoying the Legends Awards? You should know, I'm giving away #r#t04310027#s#k every day if you log in between #b12/7/2011 and 1/31/2012#k. Would you like today's #t04310027#s?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("If you want me to hold them for you, that's fine, but you better pick them up by the end of the day!")

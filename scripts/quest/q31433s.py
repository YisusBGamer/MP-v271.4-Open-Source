# 31433 - Et Tu, Tonero?
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept("Now what? As soon as #p3001005# leaves, #p3001004# calls. Do I really need to take this?"):
    sm.startQuest(parentID)
    sm.sendNext(u"\u201cGreetings, protagonist! It is I, #b#p3001004##k. Await my arrival with great anticipation!\u201d\r\nClick.\r\n")
else:
    sm.sendNext("This is getting kinda old...")

# 12414 - Excavated: Wayfinder
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("Hey, I'm composing a poem... Where is north? Which way is east? As my hand turns, you'll fight a beast! Wait, no, eat a feast! That's terrible... I have to admit, I don't know from poems."):
    sm.startQuest(parentID)
else:
    sm.sendNext("What do you think about directions? Are you for, or against them?")

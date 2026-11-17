# 11503 - [Legends] More Alchemy!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #b#e#h0##n#k! I see your profession level went up. Here is a #r#t04310027##k for you. Do you want it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Sure. Talk to me again when you are ready.")

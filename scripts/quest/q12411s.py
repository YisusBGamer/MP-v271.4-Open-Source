# 12411 - Hot-Air Balloon Motor
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("What do I do... what do I do... what do I do... \r\n#b(Spiegelmann is in quite a tizzy.)#k"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hey, #b#h0##k. I didn't peg you for the kind of person that'd just leave a person in need...")

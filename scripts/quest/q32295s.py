# 32295 - Retrieving the Seal Stone
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Athena Pierce (1012100), taken from the other quest phase

sm.setSpeakerID(1012100)  # Athena Pierce
if sm.sendAskAccept("I'm not letting anyone take it from me this time. Athena Pierce or bust! \r\n\r\n#r(Accept to immediately be moved to the Bowman Instructional School in Henesys, or so you hope. )#k"):
    sm.startQuest(parentID)
    sm.sendNext("To Henesys!")
else:
    sm.sendNext("Sugar, Olive, and the others can make it back on their own. I've got to get to Henesys!")

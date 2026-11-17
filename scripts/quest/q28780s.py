# 28780 - [Repeatable] Alien Annihilation
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
if sm.sendAskAccept("Our fields and roads are just straight messed up from all that alien digging! \r\n\r\nI want you to go out there and really mess them up!"):
    sm.startQuest(parentID)
    sm.sendNext("We've got to get the aliens' #b#i4033190# #t4033190:##k out of their hands! That will slow them down! ")
else:
    sm.sendNext("#b#m600000000##k bad shape due to aliens. Please help us.")

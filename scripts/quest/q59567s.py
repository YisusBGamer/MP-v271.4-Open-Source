# 59567 - [Returning Player] We Meet Again! (Attendance Check)
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hi, #b#h0##k! Long time no see. We're having an Attendance Check event! Want to participate?"):
    sm.startQuest(parentID)
    sm.sendNext("Hunt #b#e300 monsters around your level#k#n and open your Attendance Log! You'll receive your stamp automatically! \r\nRemember, that's enemies #rup to 20 Levels above or 10 levels below your level#k.")
else:
    sm.sendNext("Sign up here and receive gifts to help you Level up!")

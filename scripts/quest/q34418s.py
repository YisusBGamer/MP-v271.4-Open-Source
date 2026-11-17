# 34418 - [Kerning Tower] Find Dia Mandy
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Blake (1052203)

sm.setSpeakerID(1052203)  # Blake
if sm.sendAskAccept("#b(You need Dia Mandy to start making preparations for the group's debut, but she's nowhere to be found. You should look for her at all her usual spots.)#k"):
    sm.startQuest(parentID)
    sm.sendNext("#b(...Maybe you should start by looking around #rKerning Tower Ground Floor#b?)#k")
else:
    sm.sendNext("#b(The group needs Dia Mandy's help to prepare for their debut.)#k")

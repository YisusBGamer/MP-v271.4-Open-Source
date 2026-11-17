# 57137 - The Full Report
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Yamanaka Yukimori (9130079), taken from the other quest phase

sm.setSpeakerID(9130079)  # Yamanaka Yukimori
sm.sendNext("Just as Athena Pierce feared, Nobunaga has allied himself with the nefarious Black Wings... ")
sm.sendSay("What could he be scheming that he would require their power? Perhaps his forces are weaker than I thought.")
sm.sendSay("The Oda clan was never one to rely on diluting their own power with underhanded deals. It seems unlike him.")
sm.sendSay("And using dark magic to cause chaos in Maple World... where is the rigid, structured warlord I have battled in the past?")
if sm.sendAskAccept("All this thinking is making me hungry. I'd best return to Momijigaoka... maybe that nice fellow Kenshin will have me over for dinner."):
    sm.startQuest(parentID)
    sm.sendNext("Actually, I should stop by Orbis and check on Yukimori. A familiar face might do him well.")

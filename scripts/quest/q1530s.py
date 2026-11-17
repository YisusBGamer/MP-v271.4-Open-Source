# 1530 - [Battle Mode] Elite Guardian Training
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Haktorius (9070009)

sm.setSpeakerID(9070009)  # Haktorius
if sm.sendAskAccept("Brave #h0#,\r\nI am the great and mighty Haktorius, legionnaire supreme of the Maple Alliance. \r\nI am told you are a warrior of great potential, and that is exactly what I'm looking for!\r\nThe battle with the Black Mage is drawing near. His commanders are all over. We need able-bodied lunatics like yourself to stand up and fight!\r\nI hope this letter finds you well. I also hope that you are as tough as you are made out to be, because I hate writing letters.\r\nWill you help us?"):
    sm.startQuest(parentID)
    sm.sendNext("Take on the role of an Elite Guardian and protect Maple World from the Black Mage!\r\nYou will need grace in combat and a will of steel to take on the title, but a simple exam will qualify you for duty. I will send the time and date of your exam at a later... time and date.\r\n")

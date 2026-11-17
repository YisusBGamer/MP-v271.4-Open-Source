# 25647 - The Ellin Forest Incident
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Agent N (1032212)

sm.setSpeakerID(1032212)  # Agent N
if sm.sendNext("Hey! It's been a while! I got some new intel for you, fresh out of the oven.\r\n#L0##bSpeak.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("We found a passageway to the past on top of Helios Tower. You know, by Ludibrium. The Black Mage's fingerprints are all over this. This could be the break you're looking for!"):
    sm.startQuest(parentID)
    sm.sendNext("The passageway starts at the Time Control Room on the 100th floor of Helios Tower. Good luck!")
else:
    sm.sendNext("Huh. I thought you were going after the Black Mage.")

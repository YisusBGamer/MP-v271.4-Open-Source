# 32271 - We Meet Again, Olive
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Olive? (1520007)

sm.setSpeakerID(1520007)  # Olive?
if sm.sendNext("You got the jewel back! Oh, thank goodness, brave Explorer. I was delivering that jewel to Athena Pierce when someone snatched it right out of my hands! \r\n#L0##bHey, you seem a little nervous. You okay?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I'm perfectly fine! You're so sweet to ask!\r\n#L0##bSomething's up... You were never that nice to me back at Kerning City.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Heh, heh, heh. I guess you've caught me... \r\n#b(What the heck is happening to Olive?!)#k"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Oh, you believe me? Then let me have that jewel!")

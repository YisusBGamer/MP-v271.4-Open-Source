# 13106 - [Maple Castle] The Hero's Gauntlet
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Angelic Buster (9000140)

sm.setSpeakerID(9000140)  # Angelic Buster
sm.sendNext("Over here! Hey, isn't this AWESOME? \r\nOh, right, I haven't told you what it is. Okay, welcome to the #bHero's Gauntlet#k!\r\n...Wait. Wait wait wait. You're...NOT here for the Hero's Gauntlet?\r\nYou want the #bMaple Castle clue#k? Oh...yeah...that...ha ha... ")
if sm.sendAskAccept("Are you suuuuuuuuuuuure you reeeeeeeeeeally need that?\r\n...Oh, Neinheart's getting impatient, huh...\r\nWell, I was kinda busy running this super cool, totally rad challenge for cool and rad people. Why don'tcha give the #bHero's Gauntlet#k a try while I...put the finishing touches on this clue...  "):
    sm.startQuest(parentID)
    sm.sendNext("Yesssss! You are gonna LOVE this! Okay, the #bHero's Gauntlet#k is a race against time through a spoooooky obstacle course. ")
    sm.sendSay("I'll give you something special when you enter!")
else:
    sm.sendNext("Hmph! You're no fun. And I don't like no fun!")

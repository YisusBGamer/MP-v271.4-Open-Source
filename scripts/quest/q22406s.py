# 22406 - Uncomfortable Saddle
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mir (1013000)

sm.setSpeakerID(1013000)  # Mir
sm.sendNext("Master... Ughhh...\r\n#L0##bWhat is it? Are you ill?#l\r\n#k")
sm.sendSay("I... I... can't... breathe...\r\n#L0##bYou can't breathe? Why? What's wrong?! Are you hurt?!#l\r\n#k")
sm.sendSay("No, no, that's not it... Ughhhhh...\r\n#L0##bWhat is it then? Tell me!#l\r\n#k")
sm.sendSay("The saddle's too tight! \r\n#L0##bHuh...?#l\r\n#k")
sm.sendSay("Ungh... I can't even move my wings! This saddle is much too small! It doesn't fit me! I don't think I could have you ride on me like this!\r\n#L0##bOh, no... What should we do?#l\r\n#k")
sm.sendSay("I need a new saddle, master! You need to go back to #p2060005# and ask him to make another one.")
sm.startQuest(parentID)

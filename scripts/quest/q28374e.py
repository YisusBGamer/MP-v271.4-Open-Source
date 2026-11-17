# 28374 - Welcome Back to New Leaf City Day 5
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. \r\n\r\nThe Haunted House, located to the west of my fair city, is one spooky place. What is the name of its owner?\r\n#b#L0#Porter#l\r\n#L1#Prendergast#l\r\n#L2#Perzen#l\r\n#L3#Putzki#l\r\n#L4#Phyllia#l\r\n#k")
sm.sendSay("Well done! I just love visiting the Haunted House! So spooky!")
sm.tryCompleteQuest(parentID)

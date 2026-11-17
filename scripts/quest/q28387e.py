# 28387 - Welcome Back to New Leaf City Day 3 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. Who is Ridley?\r\n\r\n\r\n#b#L0#Butler in the Haunted House#l\r\n#L1#Dermatologist inside the New Leaf Mall#l\r\n#L2#Guy in the Bigger Ben Lobby#l\r\n#L3#Statue in front of Crimsonwood Keep#l\r\n#k")
sm.sendSay("Well done! I guess you've visited the marvelous Crimsonwood Keep!")
sm.tryCompleteQuest(parentID)

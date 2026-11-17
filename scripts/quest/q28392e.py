# 28392 - Welcome Back to New Leaf City Day 7 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. \r\n\r\nWho in New Leaf City is \"too cool for school\"?\r\n#b#L0#Jack Masque#l\r\n#L1#Icebyrd Slimm#l\r\n#L2#The Glimmer Man#l\r\n#L3#T-1337#l\r\n#L4#Lita Lawless#l\r\n#k")
sm.sendSay("Well done! You really know all about our residents, don't you?")
sm.tryCompleteQuest(parentID)

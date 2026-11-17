# 28385 - Welcome Back to New Leaf City Day 1 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. How long is the New Leaf City to Kerning City subway ride?\r\n\r\n#b#L0#25 seconds#l\r\n#L1#40 seconds#l\r\n#L2#55 seconds#l\r\n#L3#70 seconds#l\r\n#k")
sm.sendSay("Well done! I expect that you've ridden your share of that subway, eh?")
sm.tryCompleteQuest(parentID)

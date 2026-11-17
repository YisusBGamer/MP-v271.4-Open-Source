# 28375 - Welcome Back to New Leaf City Day 6
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. \r\n\r\nUnder our feet are bricks that serve as the foundation of our marvelous NLC buildings. How many barred windows and drain pipes are between them? Wait, multiply that number by...3!\r\n#b#L0#27#l\r\n#L1#30#l\r\n#L2#36#l\r\n#L3#39#l\r\n#L4#42#l\r\n#k")
sm.sendSay("Well done! Glad you can count!")
sm.tryCompleteQuest(parentID)

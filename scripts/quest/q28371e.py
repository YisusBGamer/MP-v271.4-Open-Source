# 28371 - Welcome Back to New Leaf City Day 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icebyrd Slimm (9201050)

sm.setSpeakerID(9201050)  # Icebyrd Slimm
sm.sendNext("Here's my question of the day. Whose face is on the New Leaf Mall sign?\r\n\r\n\r\n#b#L0#Professor Foxtrot#l\r\n#L1#Professor Foxwit#l\r\n#L2#Professor Leaftrot#l\r\n#L3#Professor Leafwit#l\r\n#L4#Professor Nitwit#l\r\n#k")
sm.sendSay("Well done! After spending so much time here, of course you know the people and places of NLC like the back of your hand, hmm?")
sm.tryCompleteQuest(parentID)

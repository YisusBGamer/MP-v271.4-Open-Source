# 2151 - Truth of the Rumor- Dances with Balrog
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Dances with Balrog (1022000)

sm.setSpeakerID(1022000)  # Dances with Balrog
if sm.sendNext("What is your business this time?\r\n#b#L0#Do you know anything about a ghost tree?#l") != 0:
    sm.dispose()
if sm.sendSay("A ghost tree... I guess you're talking about #b#o3220000##k.\r\n#b#L0#What is this #o3220000#?#l") != 0:
    sm.dispose()
if sm.sendSay("It's a very old tree surviving all the years ever since #m102000000# was covered with lush forests. As it weathered all the pains and tough times, its anger grew large. It was infuriated at the people destroying forests, and at the scenes of forests falling barren.\r\n#b#L0#What happened next?#l") != 0:
    sm.dispose()
sm.sendSay("In the end, the rage turned the tree into a monster. Now it's nothing but a horrendous monster gobbling up nutrients from the soil. Don't go too far into there. I'd praise and encourage your curiosity, but it is the king of all stumps. You should never underestimate its power.")
sm.startQuest(parentID)

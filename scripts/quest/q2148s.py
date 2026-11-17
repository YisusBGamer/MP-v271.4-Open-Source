# 2148 - Truth of the Rumor-Blackbull
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Blackbull (1020000)

sm.setSpeakerID(1020000)  # Blackbull
if sm.sendNext("What's the matter?\r\n#b#L0#Have you heard about the Ghost Tree?#l") != 0:
    sm.dispose()
if sm.sendSay("A ghost tree? Ah, you must be talking about the giant stump, right? I heard from my father that his father used to see such trees when he was young. The story says that each branch of the tree bears a red piece of cloth, which was dyed in the blood of ghosts. I have never seen it in person, so I don't know whether or not it's true.\r\n#b#L0#You didn't catch anything else?#l") != 0:
    sm.dispose()
sm.sendSay("I'm sorry, but I'm not really good at following rumors.")
sm.startQuest(parentID)

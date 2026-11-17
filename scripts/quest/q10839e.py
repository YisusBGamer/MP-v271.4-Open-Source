# 10839 - Gold Richie's Bribe
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("Did you catch it? What about #bZeno's Gloves#k?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hmm... It's in decent condition... Impressive. This will help us escape Black Wings' surveillance for a bit.")

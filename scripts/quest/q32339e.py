# 32339 - The Phantom Signal
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Small Bridge (1520045)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(1520045)  # Small Bridge
    sm.sendNext("(Hey... Why did the Empress of Ereve have such a large piece of poster board handy, anyway? And why did it already say 'PHANTOM' in glitter letters at the top?)")

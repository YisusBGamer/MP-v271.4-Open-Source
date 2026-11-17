# 21738 - Sealed Garden
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiruna (2032001)

sm.setSpeakerID(2032001)  # Spiruna
sm.sendNext("You, there. Your destiny seems to be special. Your long life, your history, that terrible curse, and the willpower you used to overcome all that... Who are you exactly?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Well, anything's fine. The fortune already tells me that I should tell you everything there is to know about #m920030001#...")

# 2325 - James's Whereabouts (1)
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: James (1300008)

sm.setSpeakerID(1300008)  # James
sm.sendNext("I... I am scared... Please...please help me...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("What? My brother sent you here? Ahhh... I am safe now. Thank you so much...")

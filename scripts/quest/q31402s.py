# 31402 - Specters Attack
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31550.

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept(u"There\u2019s a good chance the #b#m401000000##k is a Specter nest right now. I better get myself set for some righteous battle."):
    sm.startQuest(parentID)
    sm.sendNext("Preparations complete. Time to unleash the beast on these punks.")
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070000)  # Occupied Reclamation HQ
else:
    sm.sendNext("I need to prepare more. This is going to be an epic fight.")

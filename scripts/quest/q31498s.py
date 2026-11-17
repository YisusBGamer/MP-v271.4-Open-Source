# 31498 - Through the Gate
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001100), taken from the other quest phase
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31566.

sm.setSpeakerID(3001100)  # Edea
sm.sendNext("That signal... #b#p3001000##k removed the seal. ")
if sm.sendAskAccept(u"Getting inside the castle should be easy now. It\u2019s time to show them the power of Kaiser!"):
    sm.startQuest(parentID)
    sm.sendNext(u"Get ready to meet your maker, Specters. I\u2019m coming.")
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401071900)  # Tyrant's Castle Battleground
else:
    sm.sendNext("Just need to catch my breath first.")

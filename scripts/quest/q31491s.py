# 31491 - Counteroffensive
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001100)
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31559.

sm.setSpeakerID(3001100)  # Edea
sm.sendNext("The small-scale attacks, Specters invading without leadership... I would almost say that our enemies are attempting to stall for time. Our own soldiers are quite tired, but this may be our opportunity to drive out the enemy for good.")
if sm.sendAskAccept(u"Our first step is to chase out the remnants of the Specter army. They may even lead us to the main force, though all the attacks on the rear of the central regions would indicate pretty strongly where they\u2019re hiding."):
    sm.startQuest(parentID)
    sm.sendNext("It's highly probable that the main army is hiding just right of the central region, penetrating through the forest that cuts across the valley. That would be a good place to start looking. ")
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401072800)  # Occupied Heliseum Downtown, then the Foggy Valley
else:
    sm.sendNext("Talk to me again when your battle preparations are complete.")

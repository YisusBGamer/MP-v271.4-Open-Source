# 3192 - Barrier Totem
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Krag (9073005)

sm.setSpeakerID(9073005)  # Krag
if sm.sendAskAccept("You brough all the materials. Give me a moment."):
    sm.startQuest(parentID)
    if not sm.hasItem(2430180):
        sm.giveItem(2430180)  # the Barrier Totem he hands over here (the quest's Act.img doesn't give it)
    sm.sendNext("Here, take this #bBarrier Totem#k. If you put it up at #bForest of Dead Trees IV#k where Lich appears, it will block his forbidden magic.")
else:
    sm.sendNext("Just wait a bit... You really are impatient.")

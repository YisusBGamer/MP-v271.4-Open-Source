# 15245 - Heroes Ring 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Roco (1540448)

sm.setSpeakerID(1540448)  # Roco
sm.sendNext("I see you've cleared an act of #bHeroes of Maple#k. Here's your ring! ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I gave you a #bHeroes Ring#k. I can give a more powerful ring with each new act you clear. Try clearing them all!")

# 25868 - Buying Time
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cartalion (3000000)

sm.setSpeakerID(3000000)  # Cartalion
sm.sendNext("You remain a constant beacon of hope for me... I mean, for our troops, Angelic Buster. Magnus is readying a final push from the south to get through the weakened Protective Shield.")
if sm.sendAskAccept("The troops there are too weak to hold them off. Would you lend them your hand?\r\n#r(Press Accept to move to the battlefield.)#k"):
    sm.startQuest(parentID)
    sm.sendNext("Your radiance will inspire the men while your cannons blow up everything around them. I will get that shield working again. I promise. I still owe you a dinner...")
else:
    sm.sendNext("I have asked too much of you again.")
# Battlefield quest: accepting moves you to South Border Battleground 1; winning it (portal/finish_warZone0.py)
# sets record 25800, which Cartalion's end check waits for.
if sm.hasQuest(parentID):
    sm.warpInstanceIn(400050800)  # South Border Battleground 1
sm.dispose()

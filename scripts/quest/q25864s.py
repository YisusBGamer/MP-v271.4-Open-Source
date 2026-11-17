# 25864 - Specter Smasher
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cartalion (3000000)

sm.setSpeakerID(3000000)  # Cartalion
sm.sendNext(u"I need you out there right away. We can talk more later. Please, be careful. I couldn\u2019t bear it if you were to be injured on my orders... #r\r\n(Press Accept to move to the battlefield.)#k")
sm.startQuest(parentID)
# Battlefield quest: accepting moves you to North Border Battleground 1; winning it (portal/finish_warZone0.py)
# sets record 25798, which Cartalion's end check waits for.
if sm.hasQuest(parentID):
    sm.warpInstanceIn(400050400)  # North Border Battleground 1
sm.dispose()

# 31440 - Operation, Go!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001000)
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31554.

sm.setSpeakerID(3001000)  # Edea
sm.sendNext("We are now prepared for battle. All the intelligence you have gathered and all the efforts of our troops have led to this.")
sm.sendSay("We can only win this battle if everyone does their best. Now, your role will be the bait.")
sm.sendSay("Fight the Specters head-on, and pretend to lose and retreat so that they chase you. You must lure them to the valley area!")
sm.sendSay("This will be a crucial battle. We must be absolutely prepared before departing.")
if sm.sendAskAccept("Are you ready for the fight of your life?"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070600)  # Occupied Heliseum Downtown, then the Foggy Valley
else:
    sm.sendNext("Well, do what you have to do and ready up. We don't have time to waste.")

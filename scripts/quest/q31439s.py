# 31439 - Rescuing Captain Tiron
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001000)
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31553.

sm.setSpeakerID(3001000)  # Edea
sm.sendNext("This is bad...the Specters kidnapped Captain #b#p3001007##k. I dispatched all available personnel, but I don't think they're going to find him.")
sm.sendSay("I need your help with this. You're strong enough to make a frontal assault and break their lines yourself. That's just what I need.")
sm.sendSay("If the stalemate is here on the map, then you should attack from here to throw the enemies off-balance. You should have no problem with the element of surprise.")
if sm.sendAskAccept("Remember that rescuing #b#p3001007##k takes priority over wiping out the enemies! Are you ready to risk your life on the battlefield?"):
    sm.startQuest(parentID)
    sm.sendNext("I'll guide you to the battlefield. Good luck.")
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070300)  # Occupied Nameless Forest
else:
    sm.sendNext("Now's not the time to slack off!")

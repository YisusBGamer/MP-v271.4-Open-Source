# id 39401 (Just Match the Headcount), 1st Floor West Passageway (410000401), the Regulator Sergeant (3004951)
# Kain's tutorial. The game data has no lines for it: written from its quest log.
execfile("scripts/quest/_kain_tutorial.py")

npc(SERGEANT, "At your service.")
npc(SERGEANT, "Drakas. Today's assignment is one body short. You will fill the headcount.")
me("......")
sm.resetParam()
sm.setSpeakerID(SERGEANT)
if sm.sendAskAccept("Report to the #bRegulator#k in #b#m410000416##k. Obey the rules."):
    begin(parentID)
    sm.chatScript("Talk with the Regulator in East Side 1.")
sm.dispose()

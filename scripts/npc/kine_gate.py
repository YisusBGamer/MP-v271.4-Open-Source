# Friend Gate (1531029, Magician Association) / Maple Gate (1531030, Hideout HQ): Kinesis's world <-> Maple World
field = sm.getFieldID()
if field == 101020400:
    if sm.sendAskYesNo("Go through the gate to the #bHideout#k?"):
        sm.warp(331001000, 4)
elif field == 331001000:
    if sm.sendAskYesNo("Go through the gate to #bMaple World#k (#m101020400#)?"):
        sm.warp(101020400, 5)
sm.dispose()

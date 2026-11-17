# Eurenth the Sage (34717) has no start script: "You will automatically move to the relevant field." (Act.img:
# Secret Merchant Meet-Up). The Cadena story never hands out the D-03 Transmitter it asks you to use there.
TRANSMITTER = 2430947
if sm.hasQuest(parentID):
    if not sm.hasItem(TRANSMITTER) and sm.canHold(TRANSMITTER):
        sm.giveItem(TRANSMITTER)
    if sm.getFieldID() != 401020300:
        sm.warp(401020300, 0)
sm.dispose()

# Seren phase maps: leave for the Palace Main Hall
from net.swordie.ms.constants import BossConstants

others = [c for c in sm.getField().getChars() if c.getId() != chr.getId()]
if not sm.hasMobsInField():
    question = "Would you like to leave?"
elif others:
    question = "Would you like to leave the fight? Your party will continue without you."
else:
    question = "Would you like to leave the fight? The fight will be lost."
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(BossConstants.SEREN_ENTRANCE, 0)
sm.dispose()

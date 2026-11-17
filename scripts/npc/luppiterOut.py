# Leave the Jupiter fight.
from net.swordie.ms.enums import BossCooldown

CHESTS = BossCooldown.Jupiter.getTemplateIds()

mobs = [m.getTemplateId() for m in sm.getField().getMobs()]
others = [c for c in sm.getField().getChars() if c.getId() != chr.getId()]
if not mobs:
    question = "Would you like to leave?"
elif all(t in CHESTS for t in mobs):
    question = "Would you like to leave? The unopened treasure will be lost."
elif others:
    question = "Would you like to leave the fight? Your party will continue without you."
else:
    question = "Would you like to leave the fight? The fight will be lost."
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(410013709, 2)
sm.dispose()

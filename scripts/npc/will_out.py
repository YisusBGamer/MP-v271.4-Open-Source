# Will leave NPC (fight maps and treasure rooms)
CHESTS = [8950113, 8950114, 8950115]

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
    sm.warpInstanceOut(450007240)
sm.dispose()

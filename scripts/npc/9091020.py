# Black Mage's last phase (450013700): the way out, as the reward map's portal (bmbossfield_out)
others = [c for c in sm.getField().getChars() if c.getId() != chr.getId()]
if not sm.hasMobsInField():
    question = "Would you like to leave?"
elif others:
    question = "Would you like to leave the fight? Your party will continue without you."
else:
    question = "Would you like to leave the fight? The fight will be lost."
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(450012500)
sm.dispose()

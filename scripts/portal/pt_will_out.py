# Will treasure rooms (Easy 450008080, Hard 450008380, Normal 450008980): back to the Diffraction Hall entrance
CHESTS = [8950113, 8950114, 8950115]

if any(m.getTemplateId() in CHESTS for m in sm.getField().getMobs()):
    question = "Would you like to leave? The unopened treasure will be lost."
else:
    question = "Would you like to leave?"
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(450007240)
sm.dispose()

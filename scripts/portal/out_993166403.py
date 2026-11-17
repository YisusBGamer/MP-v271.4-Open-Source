# Ereve Forest Road (993166403) exit: to the Physical Training Yard once Kisha has 36452.
if sm.hasQuestCompleted(36452):
    sm.warp(993166404, 0)
else:
    sm.chat("Talk to Kisha first.")
sm.dispose()

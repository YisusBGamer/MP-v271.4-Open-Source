# One-Eye (2155000), Haven: after Thanks for the Save (39204), makes sure the weekly commissions can be offered (for
# characters who finished 39204 before the hook existed).
if sm.hasQuestCompleted(39204):
    for q in (39165, 39160):
        if sm.getQRValue(q, "start") != "1":
            sm.setQRValue(q, "start", "1")
sm.setSpeakerID(2155000)
sm.sendSayOkay("Welcome back, Squishy.")
sm.dispose()

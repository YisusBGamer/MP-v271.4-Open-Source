# Somewhere in the Wellspring (993217019), during The Wellspring (38318): follow the flame on
FATED_BATTLEGROUND = 993217020
QUEST = 38318

if chr.getInstance() is not None and sm.hasQuest(QUEST):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You come to your senses in a strange place. A lone flame drifts ahead of you. Maybe following it is "
                "the way out.)")
    sm.warp(FATED_BATTLEGROUND, 0)
else:
    sm.warpInstanceOut(410007018, 0)
sm.dispose()

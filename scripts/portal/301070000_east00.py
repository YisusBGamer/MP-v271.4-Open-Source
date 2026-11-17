# 301070000 (Path to the Altar) east00 -> the altar path where Master Red Nirg waits (301070010), for Outside the
# Castle (31258). Crimsonheart Castle (Masteria story, quests 31240-31261).
if sm.hasQuest(31258) or sm.hasQuestCompleted(31258) or sm.hasQuestCompleted(31257):
    sm.warp(301070010, 0)
else:
    sm.setSpeakerType(8)
    sm.setParam(16)
    sm.sendSayOkay("I don't have any reason to go there yet.")
sm.dispose()

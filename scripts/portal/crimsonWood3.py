# id 7, field 301000000 (Top of the Citadel) -> Hall of Gales 1 (301030000)
# Crimsonheart Castle (Masteria story, quests 31240-31261): the door opens once Battle with Wind (31252) is taken.
def can_enter():
    for q in (31252,):
        if sm.hasQuest(q) or sm.hasQuestCompleted(q):
            return True
    return False


if can_enter():
    sm.warp(301030000, 1)
else:
    sm.setSpeakerType(8)
    sm.setParam(16)
    sm.sendSayOkay("I can't go there yet.")
sm.dispose()

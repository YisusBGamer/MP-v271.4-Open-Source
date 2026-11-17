# id 8, field 301000000 (Top of the Citadel) -> Hall of the Night 1 (301040000)
# Crimsonheart Castle (Masteria story, quests 31240-31261): the door opens once Battle with Predators (31255) is taken.
def can_enter():
    for q in (31255,):
        if sm.hasQuest(q) or sm.hasQuestCompleted(q):
            return True
    return False


if can_enter():
    sm.warp(301040000, 1)
else:
    sm.setSpeakerType(8)
    sm.setParam(16)
    sm.sendSayOkay("I can't go there yet.")
sm.dispose()

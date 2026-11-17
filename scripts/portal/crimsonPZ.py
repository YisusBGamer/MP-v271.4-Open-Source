# id 9, field 301000000 (Top of the Citadel) -> Guardsman Crossway (301060000)
# Crimsonheart Castle (Masteria story, quests 31240-31261): the door opens once Guards of the Castle (31260) is taken.
def can_enter():
    for q in (31260,):
        if sm.hasQuest(q) or sm.hasQuestCompleted(q):
            return True
    return False


if can_enter():
    sm.warp(301060000, 2)
else:
    sm.setSpeakerType(8)
    sm.setParam(16)
    sm.sendSayOkay("I can't go there yet.")
sm.dispose()

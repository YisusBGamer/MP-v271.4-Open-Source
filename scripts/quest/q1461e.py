#   [Job Adv] (5th job)   5th Job : Blessings of the Goddess
# Also finishes the goddesses' parts (1462-1464) for characters who started the old way and haven't met all three.

GODDESS_QUESTS = [1462, 1463, 1464]

sm.setSpeakerID(2140001)
sm.sendNext("So, you have met with the goddesses and earned their trust. I knew you would succeed.")
for q in GODDESS_QUESTS:
    if not sm.hasQuestCompleted(q):
        sm.completeQuestNoCheck(q)
sm.completeQuest(parentID)
sm.dispose()

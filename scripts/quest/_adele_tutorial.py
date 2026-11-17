# v265 Adele tutorial (Lv 1-10, the Void and Ristonia's Fountain Square): shared by its field, quest and NPC scripts
# (execfile). Quest.wz has no Say/Act data for 39600/39671/39672/39651: the dialogue follows the quest log texts and
# the NPCs' own lines (String.wz). First Sword's Calling (39651, Lv 10) is the 1st job; the Knight Statue's call gives
# the levels up to it, as the tutorial has no hunting map.

ADELE_BEGINNER = 15002
ADELE_1 = 15100

VOID = 993162001
FOUNTAIN_SQUARE = 410000300

JEROME = 3001901
BOO = 3001914
KNIGHT_STATUE = 3001918
ANOTHER_ADELE = 3001937

KNIGHT_MEETS_BOY = 39600
RISTONIA_1 = 39671
RISTONIA_2 = 39672
FIRST_CALLING = 39651  # First Sword's Calling


def npc(speaker, text):
    sm.resetParam()
    sm.removeEscapeButton()
    sm.setSpeakerID(speaker)
    return sm.sendNext(text)


def me(text):
    sm.resetParam()
    sm.removeEscapeButton()
    sm.setPlayerAsSpeaker()
    return sm.sendNext(text)


def begin(q):
    if not sm.hasQuest(q) and not sm.hasQuestCompleted(q):
        sm.startQuestNoCheck(q)


def finish(q):
    if sm.hasQuestCompleted(q):
        return False
    begin(q)
    sm.completeQuestNoCheck(q)
    return True


def first_job():
    # Lv 10 (the level-up advances the job), then 39651; stats as a Lv 10 Adele made in town
    if chr.getJob() != ADELE_BEGINNER:
        return
    if chr.getLevel() < 10:
        sm.addLevel(10 - chr.getLevel())
    sm.questJobAdvance(FIRST_CALLING, ADELE_BEGINNER, ADELE_1)
    if chr.getJob() != ADELE_1:
        return
    sm.setSTR(4)
    sm.setDEX(4)
    sm.setINT(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    if chr.getMaxHP() < 450:
        sm.setMaxHP(450)
    if chr.getMaxMP() < 300:
        sm.setMaxMP(300)


def skip_tutorial():
    # Ristonia 2 (39672) stays open: it marks a played tutorial (Escaping the Mansion is then played, not completed)
    sm.setSpeakerID(2007)
    sm.removeEscapeButton()
    sm.lockInGameUI(True)
    if not sm.askTutorialSkip(FOUNTAIN_SQUARE):
        sm.lockInGameUI(False)
        return False
    for q in (KNIGHT_MEETS_BOY, RISTONIA_1):
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    first_job()
    sm.lockInGameUI(False)
    sm.warp(FOUNTAIN_SQUARE)
    return True

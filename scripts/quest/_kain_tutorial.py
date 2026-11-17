# v265 Kain tutorial (Lv 1-10, Toolen City): shared by its field, quest and NPC scripts (execfile).
# Quest.wz has no Say/Act data for 39401/39402/39495: the dialogue follows the quest log texts and the NPCs' own
# lines (String.wz). The tutorial's 1st job and Lv 10 come from First Malice (39402), as 39403 needs Lv 10 right after.

KAIN_BEGINNER = 6003
KAIN_1 = 6300

OPENING = 993164003  # silent copy of the 1st Floor West Passageway
TOOLEN = 410000401  # 1st Floor West Passageway
EAST_SIDE_1 = 410000416

SERGEANT = 3004951
REGULATOR = 3004952

HEADCOUNT = 39401  # Just Match the Headcount
FIRST_MALICE = 39402
FIRST_JOB_QUEST = 39495  # Kain 1st Job Advancement


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
    # Lv 10 (the level-up advances the job), then 39495; stats as a Lv 10 Kain made in town
    if chr.getJob() != KAIN_BEGINNER:
        return
    if chr.getLevel() < 10:
        sm.addLevel(10 - chr.getLevel())
    sm.questJobAdvance(FIRST_JOB_QUEST, KAIN_BEGINNER, KAIN_1)
    if chr.getJob() != KAIN_1:
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
    sm.setSpeakerID(2007)
    sm.removeEscapeButton()
    sm.lockInGameUI(True)
    if not sm.askTutorialSkip(TOOLEN):
        sm.lockInGameUI(False)
        return False
    first_job()
    for q in (HEADCOUNT, FIRST_MALICE):
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.lockInGameUI(False)
    sm.warp(TOOLEN)
    return True


def first_malice():
    # the Regulator's Malice Stone: Kain's Malice comes back (1st job)
    begin(FIRST_MALICE)
    npc(REGULATOR, "Malice Stone issued. Use it before your next assignment.")
    sm.lockInGameUI(True, False)
    try:
        sm.playSound("Sound/SoundEff.img/kain/malice")
        sm.blind(1, 200, 0, 0, 0, 500)
        sm.sendDelay(800)
        sm.blind(0, 0, 0, 0, 0, 500)
        sm.sendDelay(500)
    finally:
        sm.lockInGameUI(False, True)
    sm.setQRValue(FIRST_MALICE, "dummy", "clear")
    sm.completeQuestNoCheck(FIRST_MALICE)
    me("(Malice is flowing through my body again. My strength is coming back.)")
    first_job()
    if chr.getJob() == KAIN_1:
        sm.resetParam()
        sm.sendSayOkay("You have advanced to #bKain (1st job)#k.")
        sm.chatScript("Talk with the Regulator again.")

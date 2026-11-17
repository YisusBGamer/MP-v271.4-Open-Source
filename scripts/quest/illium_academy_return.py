# Opened by the server (Illium.offerAcademyReturn) at login and on level-up: an Illium of Lv. 30+ still on his 1st job
# who isn't at the Crystal Academy. The tutorial skip leaves out the academy story (34800-34816, no rewards of their
# own) that leads to Statue Remodeling (34817, 2nd job) at the Illium Statue in the Practice Lab: those quests are
# marked done here, with the records the Practice Lab's scenes check, and Ex offers the way back.
EX = 3001300
PRACTICE_LAB = 402000529

if chr.getJob() == 15200 and chr.getLevel() >= 30 and not sm.hasQuestCompleted(34817):
    if not sm.hasQuestCompleted(34816) and not sm.hasQuest(34800) and not sm.hasQuestCompleted(34800):
        for q in range(34800, 34817):
            if not sm.hasQuestCompleted(q):
                sm.completeQuestNoRewards(q)
        sm.createQuestWithQRValue(34813, "529=1;d=1;m=0;item2=1;exp=1;031=1;d2=1")
        sm.createQuestWithQRValue(34816, "d=1;fin=1;exp=1;d1=1")
    if sm.hasQuestCompleted(34816):
        sm.setSpeakerType(3)
        sm.setParam(37)
        sm.setColor(1)
        sm.setInnerOverrideSpeakerTemplateID(EX)
        if sm.sendAskYesNo("#face0#Sir, your power has grown enough to stabilize a new form. The #bIllium Statue#k in "
                           "the #bPractice Lab#k of the Crystal Academy is waiting to be remodeled.\r\n\r\n"
                           "Shall we return to the #m402000529# now?\r\n\r\n"
                           "#b(If you accept, you'll be moved to the #m402000529#. Talk to the Illium Statue there to "
                           "begin your 2nd job advancement.)#k"):
            sm.warp(PRACTICE_LAB)
        else:
            sm.sendNext("#face0#Very well. I'll remind you later, sir.")
sm.dispose()

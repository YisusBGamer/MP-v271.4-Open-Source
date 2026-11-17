# 20810 - [Job Adv] (Lv.30)   Mihile
# The quest is started (it needs the 1st job), the job advances, and completing it gives Quest.wz's rewards (Gladius
# 1302008 and medal 1142400); Mihile's next quests wait for "Apprentice Knight".
GLADIUS = 1302008
MEDAL = 1142400
sm.setSpeakerID(2520025)
if sm.sendAskYesNo("Congratulations on passing your trials, do you want to become one of my knights?"):
    if not sm.canHold(GLADIUS) or not sm.canHold(MEDAL):
        sm.sendSayOkay("You need inventory space.")
    elif chr.getJob() == 5100:
        sm.startQuest(parentID)
        sm.jobAdvance(5110)
        sm.completeQuest(parentID)
    else:
        sm.sendNext("You are not a mihile class.")

# 130020000 - Entrance to Drill Hall
from net.swordie.ms.loaders import MobData
if not sm.hasQuest(20871) and not sm.hasQuest(20872) and not sm.hasQuest(20873) and not sm.hasQuest(20874) and not sm.hasQuest(20875):  # KoC 2nd JobAdv Quests
    sm.chat("Sorry, only knights looking to job advance to the second job may enter here.")
else:
    MobData.getMobInfoById(9001011).setDeathScript("cygnus_exam_kill")  # 30 Tigurus -> Proof of Exam
    sm.warp(913001000, 0, True)
sm.dispose()

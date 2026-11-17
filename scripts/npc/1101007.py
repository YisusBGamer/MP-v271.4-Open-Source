# Hawkeye (1101007): the Knighthood Exam (20875, the 2nd job) is 30 Tigurus of Exam, which live only in the Second Drill Hall
# (913001000). Nothing else leads there, so the knight sends you while the exam is under way.
from net.swordie.ms.loaders import MobData
QUEST = 20875
DRILL_HALL = 913001000

sm.setSpeakerID(1101007)
quest = chr.getQuestManager().getQuestById(QUEST)
if sm.hasQuest(QUEST) and quest is not None and not quest.isComplete(chr):
    if sm.sendAskYesNo("Your Knighthood Exam: defeat 30 #b#o9001011##k in the #bSecond Drill Hall#k, then come back to me with the #bProof of Exam#k."
                       "\r\n\r\nShall I send you there? (A Return Scroll brings you back to Ereve; @stuck takes you to Henesys.)"):
        MobData.getMobInfoById(9001011).setDeathScript("cygnus_exam_kill")  # 30 Tigurus -> Proof of Exam
        sm.warp(DRILL_HALL)
elif sm.hasQuest(QUEST):
    sm.sendSayOkay("You've defeated the Tigurus. Open your Quest window to report to me.")
else:
    sm.sendSayOkay("A knight's training never ends. Keep at it.")
sm.dispose()

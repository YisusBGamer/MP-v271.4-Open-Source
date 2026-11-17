# 914000100
# "Find the Missing Kid 1" (21000) needs record 21002 = "1" to start; leaving first would overwrite it.
# v265 blocks the old prologue quests (21000/21001): Aran's story starts at the Ice Cave with The Return of the Hero
# (36901, 140090010), so a character that can't take 21000 goes there.
from net.swordie.ms.loaders import QuestData

V265_START = 140090010
old = QuestData.getQuestInfoById(21000)
if not sm.hasHadQuest(21000) and (old is None or old.isBlocked()):
    sm.chat("The battle against the Black Mage is long over... You wake up in an ice cave.")
    sm.warp(V265_START, 0)
    sm.dispose()
if not sm.hasHadQuest(21000):
    if sm.getQRValue(21002) != "1":
        sm.chat("Talk to Athena Pierce before you leave.")
    else:
        sm.chat("Accept Athena Pierce's request (Find the Missing Kid 1) before you leave.")
    sm.dispose()
else:
    sm.removeSkill(20000017)
    sm.removeSkill(20000018)
    sm.giveSkill(20000017)
    sm.giveSkill(20000018)
    sm.setQRValue(21002, "arr0=o;mo1=o;mo2=o;mo3=o", False)
    sm.warp(914000200, 1)

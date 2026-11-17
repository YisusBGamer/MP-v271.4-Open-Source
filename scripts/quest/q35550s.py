# 35550 [Daily Quest] Tenebris Research: Velivah hands out three of the day's hunting missions for the
# furthest Tenebris area the character has reached (Moonbridge 35560-35565, Labyrinth 35570-35583, Limina 35590-35594).
import random

from net.swordie.ms.loaders import QuestData

MOONBRIDGE = range(35560, 35566)
LABYRINTH = range(35570, 35584)
LIMINA = range(35590, 35595)
MISSIONS = 3

qm = chr.getQuestManager()


def taken(q):
    # in progress, or completed since the last reset (a completion before the reset may be redone)
    if qm.hasQuestInProgress(q):
        return True
    quest = qm.getQuestById(q)
    return quest is not None and qm.hasQuestCompleted(q) and not qm.canRepeat(QuestData.getQuestInfoById(q), quest)


sm.setSpeakerID(3003810)
if taken(parentID):
    sm.sendSayOkay(u"Carry out the daily missions and help the Temple Keepers with their studies.")
    sm.dispose()

level = chr.getLevel()
if level >= 255 and sm.hasQuestCompleted(35800):
    pool = list(LIMINA)
elif level >= 250 and sm.hasQuestCompleted(35700):
    pool = list(LABYRINTH)
else:
    pool = list(MOONBRIDGE)
pool = [q for q in pool if not taken(q)]
picked = random.sample(pool, min(MISSIONS, len(pool)))

text = u"Carry out the daily missions and help the Temple Keepers with their studies.\r\n\r\nToday's missions:\r\n"
for q in picked:
    text += u"\r\n#b#e#y%d##k#n" % q
if not picked:
    sm.sendSayOkay(u"There are no more missions for you today. Come back tomorrow.")
    sm.dispose()
if not sm.sendAskAccept(text + u"\r\n\r\n#b(Accept #e[Daily Quest] Tenebris Research#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
if sm.hasQuest(parentID):
    for q in picked:
        sm.startQuestNoCheck(q)
sm.dispose()

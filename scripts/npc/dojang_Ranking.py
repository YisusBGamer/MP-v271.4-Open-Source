# Mu Lung Ranking (2091010) | Mu Lung Dojo Hall: the client's ranking window (DojoRanking.openWindow), or the record
# and weekly ranking as dialogs on an older server build
try:
    from net.swordie.ms.client.character import DojoRanking
except ImportError:
    DojoRanking = None  # until the server has the weekly ranking

DOJO_QUEST = 3887
if DojoRanking is not None and hasattr(DojoRanking, "openWindow"):
    DojoRanking.openWindow(chr)
else:
    sm.setSpeakerID(2091010)
    record = ("#e<Mu Lung Dojo Record>#n\r\n\r\nBest floor: #b" + str(sm.getQRValue(DOJO_QUEST, "best") or "0") +
              "#k\r\nLast attempt: floor #b" + str(sm.getQRValue(DOJO_QUEST, "floor") or "0") + "#k in #b" +
              str(sm.getQRValue(DOJO_QUEST, "time") or "0") + "#k seconds\r\nDojo Points: #b" +
              str(sm.getQRValue(DOJO_QUEST, "point") or "0") + "#k")
    if DojoRanking is None:
        sm.sendSayOkay(record)
    else:
        best = DojoRanking.thisWeekBest(chr)
        if best is not None:
            record += "\r\nThis week: floor #b" + str(best[0]) + "#k in #b" + str(best[1]) + "#k seconds"
        choice = sm.sendNext(record + "\r\n\r\n#b#L0#This week's Novice ranking (Lv. 105-200)#l\r\n"
                             "#L1#This week's Master ranking (Lv. 201+)#l\r\n"
                             "#L2#Last week's Novice ranking#l\r\n"
                             "#L3#Last week's Master ranking#l")
        if 0 <= choice <= 3:
            grp = DojoRanking.NOVICE if choice % 2 == 0 else DojoRanking.MASTER
            sm.sendSayOkay("#e<" + ("Last week's " if choice >= 2 else "This week's ") +
                           ("Novice" if grp == DojoRanking.NOVICE else "Master") + " ranking>#n\r\n\r\n" +
                           DojoRanking.top(grp, choice >= 2, 10))
sm.dispose()

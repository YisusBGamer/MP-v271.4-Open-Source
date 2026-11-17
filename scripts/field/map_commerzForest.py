# id 865030100 (Commerci Republic : Sospetto Forest Trail), field 865030100
sm.playSound("Sound/FarmSE.img/boxResult", 100)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)
sm.closeUI(1128)

# Sospetto Forest Heart (865030500): Il Capo shows himself for Who's the Boss? (17707) and [Mission] Eliminate Il Capo
# (17923) - he isn't in the map's data
if sm.getFieldID() == 865030500 and chr.getField().getLifeByTemplateId(9390866) is None:
    for q in (17707, 17923):
        if sm.hasQuest(q) and not sm.isComplete(q):
            sm.chatScript("Il Capo, the head of the Assassins Guild, is here!")
            sm.spawnMob(9390866, 100, 90, False)
            break

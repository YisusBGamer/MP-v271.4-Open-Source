# 401010010 Forest of Choices. Push the Line (31406): "take back the Forest of Choices" - the battle itself is in the
# Occupied Twisted Forest (q31406s.py moves you there on accepting). Kaisers on the quest who haven't won it, or won it
# without the Specter Commander Badge it also needs, can go into the battle from here.
if sm.hasQuest(31406) and (sm.getQRValue(31551) != "1" or not sm.hasItem(4033361)):
    sm.setPlayerAsSpeaker()
    if sm.sendAskYesNo("The Specter Shieldbearers are gathered further in. Push the line back now?\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
        sm.warpInstanceIn(401070100)  # Occupied Twisted Forest
sm.dispose()

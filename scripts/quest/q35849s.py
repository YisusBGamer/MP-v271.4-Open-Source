# [Limina] The Final Foe (35849): Cygnus in the Giant's Heart; the Weakened Black Mage waits in your own copy of
# the map (field/enter_450012500), the quest ends when it falls (ready_35849)
CYGNUS = 3003993

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The final operation of the Alliance is to take the White Spear carrying the Maple Warrior to the "
                    "Black Mage. The heroes of the Alliance arrive before the Black Mage as planned.)")
        sm.setSpeakerID(CYGNUS)
        if sm.sendAskAccept("This is now the final part. Go to everyone's side. It's now time to defeat the #rBlack "
                            "Mage#k."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dir", "1")
        if sm.getChr().getInstance() is None:
            sm.warpInstanceIn(450012500, 0)
sm.dispose()

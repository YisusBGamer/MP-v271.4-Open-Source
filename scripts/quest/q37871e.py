# [Esfera] Mirrors in Mirrors (37871), handing it in to Will after his Reflection falls; back to the Radiant Throne
WILL = 3003559

if sm.hasQuest(parentID):
    if sm.completeQuest(parentID):
        sm.setSpeakerID(WILL)
        sm.setPlayerAsSpeaker()
        sm.sendNext("(In the end you couldn't prevent the ritual, and Tana was absorbed by the Black Mage. But all hope "
                    "isn't lost. The final battle with the #rBlack Mage#k approaches.)")
        if sm.getChr().getInstance() is not None:
            sm.warpInstanceOut(450007240, 0)
sm.dispose()

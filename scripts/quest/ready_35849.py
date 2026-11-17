# [Limina] The Final Foe (35849): the Black Mage has fallen. Then the White Mage (35850) and the End of War (35851),
# whose NPCs only appear in these scenes, then back to the Outpost, where Defying Destiny (35815) starts.
WHITE_MAGE, CYGNUS = 3003994, 3003995

sm.onLayer(800, "bmf0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_soul/1", 4, False, -1, False)
sm.setPlayerAsSpeaker()
sm.sendNext("(You finally succeeded in defeating the #rBlack Mage#k. But... It seems you have vanished.)")
done = sm.completeQuest(parentID)
sm.offLayer(500, "bmf0", False)
if done:
    if not sm.hasQuest(35850) and not sm.hasQuestCompleted(35850):
        sm.startQuest(35850)
    if sm.hasQuest(35850):
        sm.setSpeakerID(WHITE_MAGE)
        sm.sendNext("...")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(To your surprise, the #bWhite Mage#k appeared before you and spoke with you for a while. When you "
                    "finally regained consciousness, you found yourself on the ship.)")
        sm.setQRValue(35850, "dir", "1")
        sm.completeQuest(35850)
    if sm.hasQuestCompleted(35850) and not sm.hasQuest(35851) and not sm.hasQuestCompleted(35851):
        sm.startQuest(35851)
    if sm.hasQuest(35851):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(It seems you have revived for some reason, much to Empress Cygnus's relief.)")
        sm.setSpeakerID(CYGNUS)
        sm.sendNext("You have returned to everyone. May there be peace in Maple World for defeating the #rBlack Mage#k.")
        sm.completeQuest(35851)
    if sm.hasQuestCompleted(35851):
        if sm.getChr().getInstance() is not None:
            sm.warpInstanceOut(450009050, 0)
        else:
            sm.warp(450009050, 0)
sm.dispose()

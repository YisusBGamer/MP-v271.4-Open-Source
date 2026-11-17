# 321000000 Conference Chambers (Shadowvale) - Zero's tutorial, scene 1. Short Swordie Plus version of the prologue;
# Express skips to Henesys as before, with Zero's story skipped (see PlayStyleModule). Both complete the prologue
# quests (40000-40004, 40900).
WILL = 2410000
KYSON = 2410001
LYRA = 2410002
PROLOGUE_QUESTS = (40000, 40001, 40002, 40003, 40004, 40900)

if sm.getQRValue(40005) != "1":
    sm.setQRValue(40005, "1")  # the intro is over: Lyra offers Emergency Mission (40000)
sm.setSpeakerID(2007)
sm.removeEscapeButton()
if sm.askTutorialSkip(100000000):
    for q in PROLOGUE_QUESTS + (40905, 40914):  # Express: the story is skipped - the weapon window opens right away
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.lockInGameUI(False)
    sm.warp(100000000)
else:
    sm.lockInGameUI(True, False)
    sm.sendDelay(800)
    sm.setSpeakerID(WILL)
    sm.removeEscapeButton()
    sm.sendNext("Darklings have been gathering in Field 1-B again. Nine, you'll clear them out. Milo will go with you.")
    sm.setSpeakerID(KYSON)
    sm.sendSay("Try not to get lost on the way, rookie. Shadowvale needs every knight it has... even the useless ones.")
    sm.setSpeakerID(LYRA)
    sm.sendSay("Enough, Kyson. Nine has done more for this city than you think.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(Darklings, always right when someone starts asking questions about the Shadow Knights... I need to "
               "find out what the commanders are hiding.)")
    sm.lockInGameUI(False)
    sm.warpInstanceIn(321000300, 0)

# Kanna's tutorial (Honnou-ji, 807100100-807100112). Kanna is created in 807100100. Short Swordie Plus version of the
# story; Express skips to Momijigaoka like Hayato's tutorial (see PlayStyleModule). Ends in 807040000, where
# hayatoJobChange makes her a Kanna.
from net.swordie.ms.client.character import PlayStyleModule
HARUAKI = 9131010
KENSHIN = 9131006
HAKU = 9130081
RANMARU = 9131004
ODA_SOLDIER = 9421505
AKECHI_SOLDIER = 9421507


def skip_tutorial():
    sm.setSpeakerID(2007)  # Maple Administrator
    sm.removeEscapeButton()
    sm.lockInGameUI(True)
    if sm.askTutorialSkip(807040000):
        if chr.getLevel() < 10:
            sm.addLevel(10 - chr.getLevel())
        for quest in (32202, 32203, 32215):  # as Hayato's tutorial skip
            sm.completeQuestNoRewards(quest)
        sm.lockInGameUI(False)
        sm.warp(807040000)
        return True
    return False


if PlayStyleModule.isSkipRun(chr):  # Express chosen in @menu later in the tutorial
    skip_tutorial()
    sm.dispose()

field = sm.getFieldID()
if field == 807100100:  # Honnou-ji Western Grounds
    if not skip_tutorial():
        sm.lockInGameUI(True, False)
        sm.sendDelay(800)
        sm.setSpeakerID(HARUAKI)
        sm.removeEscapeButton()
        sm.sendNext("Kanna, listen closely. Oda Nobunaga isn't just fighting a war tonight. He's performing a forbidden "
                    "ritual beneath Honnou-ji - he means to tear open the boundary between worlds.")
        sm.sendSay("If that rift opens, every yokai of the netherworld will pour through. You're the only onmyouji I "
                   "have who can reach the temple in time.")
        sm.setSpeakerID(KENSHIN)
        sm.sendSay("My army will hold the western grounds. Go through the alleyway, girl. My men can't follow you "
                   "where you're going.")
        sm.setPlayerAsSpeaker()
        sm.sendSay("Leave it to me, Master Haruaki. Haku, let's go!")
        sm.lockInGameUI(False)
        sm.warpInstanceIn(807100102, 0)
elif field == 807100102:  # Honnou-ji Alleyway
    if not sm.hasMobsInField():
        for x, mob in ((-300, ODA_SOLDIER), (-100, AKECHI_SOLDIER), (100, ODA_SOLDIER), (250, AKECHI_SOLDIER),
                       (400, ODA_SOLDIER), (500, ODA_SOLDIER)):
            sm.spawnMob(mob, x, 32, False)
        sm.chatScript("Soldiers block the alleyway! Defeat them and head to the back of the temple.")
elif field == 807100103:  # Honnou-ji Temple Rear
    sm.lockInGameUI(True, False)
    sm.sendDelay(800)
    sm.setSpeakerID(HAKU)
    sm.removeEscapeButton()
    sm.sendNext("Kanna, do you feel that? The air is thick with yokai. Whatever Nobunaga is doing, it's happening right "
                "below us.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("The basement... Stay close, Haku.")
    sm.lockInGameUI(False)
    sm.warp(807100104)
elif field == 807100104:  # Honnou-ji Temple Basement
    sm.lockInGameUI(True, False)
    sm.sendDelay(800)
    sm.setSpeakerID(RANMARU)
    sm.removeEscapeButton()
    sm.sendNext("An onmyouji? You're too late. Lord Nobunaga's ritual is already complete!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Then I'll seal the rift myself! Haku, lend me your power!")
    sm.setSpeakerID(HAKU)
    sm.sendSay("Kanna, wait - the rift is pulling at us! Hold on!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(A blinding light... and then a strange wind, carrying the scent of maple leaves...)")
    sm.lockInGameUI(False)
    sm.warpInstanceOut(807040000, 0)
else:  # 807100105-807100112: scene copies nobody is meant to reach
    sm.warpInstanceOut(807040000, 0)

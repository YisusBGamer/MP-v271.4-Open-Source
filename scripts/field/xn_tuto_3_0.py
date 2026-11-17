# 931050920 Classified Lab - Xenon's tutorial, scene 2: Gelimer tests his new creation (clear_931050920 continues)
GELIMER = 2159376
if sm.getFieldID() == 931050920 and not sm.hasMobsInField():
    sm.lockInGameUI(True, False)
    sm.sendDelay(800)
    sm.setSpeakerID(GELIMER)
    sm.removeEscapeButton()
    sm.sendNext("Subject X is awake. Fascinating... the human brain survived the fusion after all.")
    sm.sendSay("Begin the combat routine test. Destroy the training units, X. Let's see what my masterpiece can do.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(My body moves on its own... Targets acquired. Weapon systems online.)")
    sm.lockInGameUI(False)
    for x in (-600, -400, -200, 200, 400, 600):
        sm.spawnMob(9300409 if x < 0 else 9300410, x, 63, False)
    sm.chatScript("Destroy the training robots.")

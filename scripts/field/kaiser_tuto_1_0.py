# 940001010 East Pantheon - Kaiser's tutorial, scene 2: the Black Mage's creatures break into Pantheon
# (clear_940001010 continues once they're defeated)
if sm.getFieldID() == 940001010 and not sm.hasMobsInField():
    sm.lockInGameUI(True, False)
    sm.sendDelay(500)
    sm.setPlayerAsSpeaker()
    sm.removeEscapeButton()
    sm.sendNext("Wraiths, inside the walls?! They must have slipped past the army. I have to stop them!")
    sm.lockInGameUI(False)
    for x in (-2700, -2400, -2100, -1500, -1200, -900):
        sm.spawnMob(9307011, x, 29, False)
    sm.chatScript("Defeat the creatures that broke into East Pantheon.")

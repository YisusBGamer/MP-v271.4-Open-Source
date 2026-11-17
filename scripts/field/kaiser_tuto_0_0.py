# 940001000 Great Temple Interior - Kaiser's tutorial, scene 1 (Kaiser is created here). Short Swordie Plus version of
# the story; Express skips to Pantheon (see PlayStyleModule).
FORMER_KAISER = 3000011
VELDEROTH = 3000007

sm.setSpeakerID(2007)
sm.removeEscapeButton()
if sm.askTutorialSkip(400000000):
    sm.lockInGameUI(False)
    sm.warp(400000000)
else:
    sm.lockInGameUI(True, False)
    sm.spawnNpc(FORMER_KAISER, 250, 27)
    sm.spawnNpc(VELDEROTH, -250, 27)
    sm.sendDelay(800)
    sm.setSpeakerID(FORMER_KAISER)
    sm.removeEscapeButton()
    sm.sendNext("The Black Mage's legions are marching on Heliseum. I ride out at dawn with the Nova army.")
    sm.sendSay("You are the most gifted student I have ever trained. While I am gone, Pantheon is in your hands.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("I won't let a single one of them past the gates, Kaiser.")
    sm.setSpeakerID(VELDEROTH)
    sm.sendSay("And I'll be right there with you, as always. Somebody has to make sure you don't get reckless.")
    sm.setSpeakerID(FORMER_KAISER)
    sm.sendSay("Good. Protect the Relic, and protect each other. Something tells me the war will come to us sooner "
               "than we think.")
    sm.sendDelay(500)
    sm.lockInGameUI(False)
    sm.warpInstanceIn(940001010, 0)

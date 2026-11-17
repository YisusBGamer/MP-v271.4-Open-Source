# 931050900 Edelstein Outskirts 1 (Peacetime Edelstein) - Xenon's tutorial, scene 1 (Xenon is created here). Short
# Swordie Plus version of the story; Express skips to the Resistance's Secret Plaza (see PlayStyleModule).
from net.swordie.ms.client.character import PlayStyleModule
if sm.getFieldID() != 931050900 and not PlayStyleModule.isSkipRun(chr):
    sm.warp(931050920)  # 931050904: the lab's exit door leads here - back into the lab
else:
    sm.setSpeakerID(2007)
    sm.removeEscapeButton()
    if sm.askTutorialSkip(310010000):
        sm.lockInGameUI(False)
        sm.warp(310010000)
    else:
        sm.lockInGameUI(True, True)
        sm.sendDelay(1000)
        sm.setPlayerAsSpeaker()
        sm.removeEscapeButton()
        sm.sendNext("(Edelstein, before the Black Wings came... Sunlight, the smell of the park, people laughing. "
                    "This is the last thing I remember of being... just me.)")
        sm.sendSay("(Then came the men in black coats. A needle. And a white room that hummed.)")
        sm.sendDelay(500)
        sm.lockInGameUI(False)
        sm.warpInstanceIn(931050920, 0)

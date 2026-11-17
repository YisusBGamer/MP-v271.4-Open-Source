# Jett tutorial | 552000030 Wrecked Airship 1: the crash on Maple World; Burke takes the Core and leaves Jett stranded
BURKE = 9270083

if chr.getLevel() < 10:
    sm.spawnNpc(BURKE, 300, 395)
    sm.lockInGameUI(True, False)
    sm.setPlayerAsSpeaker()
    sm.removeEscapeButton()
    sm.sendNext("Burke... are you all right? Where are we?")
    sm.setSpeakerID(BURKE)
    sm.sendSay("I'm fine. But I'm taking the Core, Jett. I'm tired of always being the one behind you.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("The Core? It's useless to you! Only my family can use its power!")
    sm.setSpeakerID(BURKE)
    sm.sendSay("We'll see about that. Goodbye, partner.")
    sm.removeNpc(BURKE)
    sm.setPlayerAsSpeaker()
    sm.sendSay("(Without the Core... I can barely stand. I have to follow him...)")
    sm.lockInGameUI(False)

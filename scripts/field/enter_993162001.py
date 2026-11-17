# The Void (993162001): where a new Adele starts (Lv 1). Asks SKIP or PLAY, then the opening: a prayer heard from the
# Void ([Temple of Time] A Quick Look Back, 39680). Lines other than Another Adele's (String.wz) aren't in the data.
execfile("scripts/quest/_adele_tutorial.py")

if chr.getJob() == ADELE_BEGINNER and not sm.hasQuestCompleted(KNIGHT_MEETS_BOY) and not skip_tutorial():
    sm.lockInGameUI(True, False)
    try:
        sm.blind(1, 255, 0, 0, 0, 0)
        sm.sendDelay(1500)
        npc(ANOTHER_ADELE, "You will wander this place forever, lost in your sin.")
        sm.blind(0, 0, 0, 0, 0, 1000)
        sm.sendDelay(1000)
        me("(Darkness, as far as I can see... How long have I been here?)")
        sm.playSound("Sound/SoundEff.img/adele/bell3")
        sm.sendDelay(1000)
        npc(JEROME, "#b(A boy's voice, from far away)#k\r\nPlease... Knight of the statue, if you can hear me, lend me your strength. Ristonia needs you.")
        me("(A prayer... Someone is calling me from beyond the Void.)")
        sm.playSound("Sound/SoundEff.img/adele/light")
        sm.blind(1, 255, 240, 240, 240, 1500)
        sm.sendDelay(1500)
    finally:
        sm.lockInGameUI(False, True)
    sm.warp(FOUNTAIN_SQUARE)
elif chr.getJob() == ADELE_BEGINNER:
    sm.warp(FOUNTAIN_SQUARE)
sm.dispose()

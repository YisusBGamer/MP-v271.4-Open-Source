# 321000600 Umbra Temple - Zero's tutorial, scene 3: the girl in the webs, and the Shadow Knights
# (clear_321000600 continues)
GIRL = 2410007
KYSON = 2410001
if sm.getFieldID() == 321000600 and not sm.hasMobsInField():
    sm.lockInGameUI(True, False)
    sm.spawnNpc(GIRL, 362, -269)
    sm.sendDelay(800)
    sm.setPlayerAsSpeaker()
    sm.removeEscapeButton()
    sm.sendNext("(There she is, still asleep in the webs... I've seen her in my dreams for as long as I can "
                "remember.)")
    sm.setSpeakerID(KYSON)
    sm.sendSay("I knew it. You've been sneaking off to this temple, Nine. Step away from the girl - she belongs to the "
               "commanders now.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Over my dead body, Kyson.")
    sm.lockInGameUI(False)
    sm.spawnMob(9300747, 700, 5, False)  # Kyson
    sm.spawnMob(9300748, 850, 5, False)  # Seamus
    for x in (-200, 0, 950):
        sm.spawnMob(9300745, x, 5, False)  # Shadow Knights
    sm.chatScript("Defeat Kyson, Seamus and the Shadow Knights!")

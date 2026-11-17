# 1204005 "Tru" in 910400000: the Puppeteer in disguise (Gathering Some Strange Information, Aran 21733).
# Talking to him starts the fight; clear_910400000 sets record 21762 = 2.
PUPPETEER = 9300345  # Lv31, between the ones of 21731 (9300344) and 21734 (9300346)

if sm.hasQuest(21733) and sm.getQRValue(21762) != "2":
    sm.sendNext("Yo, you made it! Heh... You really came running, just like that.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("...You're not Tru. Who are you?!")
    sm.setSpeakerID(parentID)
    sm.sendSay("Heh heh... The Black Wings send their regards, hero!")
    if not sm.hasMobsInField():
        sm.removeNpc(parentID)
        sm.spawnMob(PUPPETEER, 72, 120, False)
else:
    sm.sendSayOkay("What kind of information are you looking for? Don't be shy!")
sm.dispose()

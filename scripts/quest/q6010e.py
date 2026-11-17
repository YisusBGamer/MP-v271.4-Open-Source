# Dragon Rider (Bowman)
# 6010
# Legor (2081300)

draco = 80001306
sm.sendSayOkay("Once you are ready to accept your new companion, step up.\r\n"
               "You will now be encountering a mysterious being totally unlike anything you've ever faced.")
sm.sendSayOkay("You are the one who has already exceeded the boundaries and the limits of a human being... the only time the proud #t1902002# bows its head is to you. Hopefully you'll experience new, amazing adventures that are surely to await you with #t1902002#...")
if sm.completeQuest(parentID):
    sm.giveSkill(draco)

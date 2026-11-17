# Jett 4th Job Adv, 552000074 Black Barrier Cave (Tracking Down the Sender 53251 -> Baroq's Bunny Battlers 53252)
# Quest.wz has 53251 and 53253 requiring each other, so these quests are completed/started without the checks.
if sm.hasQuest(53251):
    sm.spawnNpc(9270092, 419, 55)
    sm.spawnNpc(9270090, 817, 55)
    sm.setSpeakerID(9270090)
    sm.removeEscapeButton()
    sm.sendNext("Ha! Just as I suspected... a great plan come to ruin because of one idiot's weak-hearted sympathy.")
    sm.setSpeakerID(9270092)
    sm.sendNext("Ugh.. How could I fall for such a simple trap?")
    sm.setSpeakerID(9270090)
    sm.sendNext("Simple traps are always the best, my red-headed friend! You stood no chance against my machinations. I told you that I knew where the Bounty Hunter was, and you brought the Bounty Hunter right to me.")
    sm.sendNext("It was devilishly easy, in the end, your childish sympathy has given me everything I wanted!")
    sm.sendNext("I've had the pleasure of playing a complex symphony with your emotions. Now, I will give you the grand finale. I will show you how I deal with traitors.")
    sm.setPlayerAsSpeaker()
    sm.sendNext("What's he talking about #p9270092#!?")
    sm.setSpeakerID(9270090)
    sm.sendNext("The power of the #bcore#k was... wilder than I expected. It took me some time to tame its more ferocious tendencies.")
    sm.sendNext("Though it could not be fully controlled without the #bcore's owner#k could it? I would have had you long ago if it weren't for this one's misdirection.")
    sm.completeQuestNoCheck(53251)
    sm.startQuestNoCheck(53252)
elif sm.hasQuest(53252):
    # back after leaving or dying: Burke and Baroq are still here
    sm.spawnNpc(9270092, 419, 55)
    sm.spawnNpc(9270090, 817, 55)
if sm.hasQuest(53252) and not sm.hasMobsInField():
    for i in range(6):
        sm.spawnMob(9420570, 596, 55, False)
    sm.chat("Defeat the Black Wing Henchmen, then talk to Burke.")

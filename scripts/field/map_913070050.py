# General Store Yard (913070050): "Unfamiliar Guests" (20034). Mihile finds Cygnus and Neinheart in the yard.
# The quest's turn-in NPC (1106000) stands in no map, so the scene completes it.

CYGNUS = 1106001
NEINHEART = 1106003

if not sm.hasQuestCompleted(20034):
    sm.lockInGameUI(True)
    sm.chatScript("General Store Yard")
    sm.forcedInput(2)
    sm.showBalloonMsg("Effect/Direction7.img/effect/tuto/step0/4", 2000)
    sm.sendDelay(2000)
    sm.forcedInput(0)

    sm.localEmotion(6, 10000, False)

    sm.removeEscapeButton()
    sm.setPlayerAsSpeaker()
    sm.sendNext("Huh? Who's that girl? And who's the guy with her?")

    sm.setSpeakerID(NEINHEART)
    sm.sendSay("Empress, this is a shop's back yard. Are you sure this is the place?")

    sm.setSpeakerID(CYGNUS)
    sm.sendSay("I'm sure, Neinheart. The light led me here.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Hey! You can't just walk into Mr. Limbert's yard. He'll make me sweep up after you.")

    sm.setSpeakerID(NEINHEART)
    sm.sendSay("A shop boy? Hm. Talk to me, boy. I'd like to see what you're made of.")

    sm.lockInGameUI(False)
    sm.completeQuestNoRewards(20034)

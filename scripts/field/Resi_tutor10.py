# Neglected Rocky Mountain (931000000): the Resistance story opens with the friends' hide-and-seek, then on to the
# rocky mountain (931000001). The skip goes to the Secret Plaza at level 10.
MAPLE_ADMINISTRATOR = 2007
JUN, ULRIKA, VON = 2159000, 2159001, 2159002
SKIP_QUESTS = (23000, 23001, 23002, 23003, 23004, 23005, 23010)
SECRET_PLAZA = 310010000
TARGET_LEVEL = 10

sm.setSpeakerID(MAPLE_ADMINISTRATOR)
sm.removeEscapeButton()

if sm.askTutorialSkip(SECRET_PLAZA):
    if chr.getLevel() < TARGET_LEVEL:
        sm.addLevel(TARGET_LEVEL - chr.getLevel())
    for quest in SKIP_QUESTS:
        sm.completeQuestNoRewards(quest)
    sm.warp(SECRET_PLAZA)
else:
    sm.setSpeakerID(ULRIKA)
    sm.sendNext("There you are, #h #! You're late. Get over here.")
    sm.setSpeakerID(VON)
    sm.flipSpeaker()
    sm.sendSay("What was the hold up? You scared or something?")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Don't be ridiculous.")
    sm.setSpeakerID(JUN)
    sm.flipSpeaker()
    sm.sendSay("You're not s-s-scared at all? I am, a little b-b-bit... The grown-ups warned us never to venture into "
               "the #bVerne Mines#k... Plus, there are all those #rBlack Wings#k around, watching us, I just know it.")
    sm.setSpeakerID(ULRIKA)
    sm.sendSay("Jun, we're already here. If we're going to get in trouble, let's at least have some fun first. "
               "Let's play hide-and-seek!\r\n#h0#, since you were late, you're it. Count to 10 and then come find us. "
               "No peeking.")
    sm.warp(931000001, 1)
sm.dispose()

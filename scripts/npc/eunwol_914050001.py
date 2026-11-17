# 2159454 Big Penguin on Riena Strait (914050001), Shade's Finding Aran 2 and 3.
#   38048: needs its record = impossible to start, clear to finish: dodge the penguins' snowballs (eunwol_dropsnow).
#   38049: same records: guess which igloo the penguins hide in (eunwol_igloo1-3, 914050007).
# The island has no exit, so each game is offered with the way back to Rien.
SNOWBALLS = 914050002
IGLOOS = 914050007
RIEN = 140020300


def stays():
    if sm.sendAskYesNo("Go back to Rien?"):
        sm.warp(RIEN, 0)
        return False
    return True


if sm.hasQuestCompleted(38047) and not sm.hasQuest(38048) and not sm.hasQuestCompleted(38048):
    sm.sendNext("Intruder! An intruder on our island! Everybody, snowballs!")
    if stays():
        sm.startQuestNoCheck(38048)
        sm.setQRValue(38048, "impossible")
        sm.warpInstanceIn(SNOWBALLS, 0)
elif sm.hasQuest(38048) and sm.getQRValue(38048) != "clear":
    sm.sendNext("You're still here, intruder?! Snowballs!")
    if stays():
        sm.warpInstanceIn(SNOWBALLS, 0)
else:
    if sm.hasQuest(38048):
        sm.questEndDialogue(38048)
    if sm.hasQuestCompleted(38048) and not sm.hasQuest(38049) and not sm.hasQuestCompleted(38049):
        sm.sendNext("You dodged all our snowballs?! You must be the kidnapper who wants to take our friend away!")
        sm.sendSay("Fine. If you can guess which igloo we're hiding in, we'll tell you where she is.")
        if stays():
            sm.startQuestNoCheck(38049)
            sm.setQRValue(38049, "impossible")
            sm.warp(IGLOOS, 0)
    elif sm.hasQuest(38049) and sm.getQRValue(38049) != "clear":
        if stays():
            sm.warp(IGLOOS, 0)
    elif sm.hasQuest(38049) or (sm.hasQuestCompleted(38049) and not sm.hasQuestCompleted(38050)):
        sm.warp(IGLOOS, 0)
    elif sm.sendAskYesNo("Go back to Rien?"):
        sm.warp(RIEN, 0)
sm.dispose()

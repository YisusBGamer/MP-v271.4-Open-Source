# Grandis at War! (32250, Explorer magicians) - Cartalion
# Rondo has to see the war for himself: the quest ends once he's been to the Eastern Border (record 32364, set by
# portal/enter_eastborder.py) and back to Rondo in Pantheon. No Say.img lines exist for the start; written from the
# quest's own summary.
CARTALION = 3000000

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(CARTALION)
    if sm.sendAskAccept("There is a small village just a ways out past Pantheon, where the #bAnima Tribe#k lives. "
                        "But the way there is blocked: Grandis is at war. See for yourselves at the #bEastern "
                        "Border#k, past the #m400010000#, if you must.\r\n#r(The quest has to be forfeited and "
                        "restarted if you fail.)#k"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.chatScript("Go east past the East Sanctum to the Eastern Border, then come back to Rondo in Pantheon.")
sm.dispose()

# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Save the Cat 1 (59003) - Arby, caught in a trap in Wolf Forest
ARBY_TRAP = 8669000
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390467)
    sm.sendNext("Mew... mew... Help... I'm stuck...")
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("A cat stuck in a trap! Don't worry, little guy, a hero is here! ...But how do I get you out? "
                        "I know, a hero always uses the right tool!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    if not sm.hasReactors():
        sm.spawnReactor(ARBY_TRAP, 286, 71)
    sm.balloonMsg("Press Ctrl to attack the trap and tear it down!")
sm.dispose()

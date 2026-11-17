# [Limina] Light of Annihilation (35846), the Resistance Soldier in End of the World 2-2. The soldiers to rescue
# (3003976-3003980) stand nowhere: the rescue is the fight on the ship's outer decks (993070078, upper right portal),
# and the soldier takes the quest back either way.
SOLDIER = 3003990

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Now that you've provided support to the Resistance ship, you talk to the soldier again.)")
    sm.setSpeakerID(SOLDIER)
    if sm.sendAskAccept("Enter through the portal from #bEnd of the World 2-2#k, rescue allied soldiers, and defeat "
                        "monsters."):
        sm.startQuest(parentID)
sm.dispose()

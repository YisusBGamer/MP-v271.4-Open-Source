# [Limina] Guard Captain Darknell (35844) in Final Horizon Fore: he is fought in your own copy (993070076); its
# clear script finishes the quest and takes you to End of the World 1-1. dir is set on accepting, as the quest
# completes by itself.
DARKNELL = 3003987

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You reached the top of the waterfall, and fought off the Black Mage's Elites. The guard captain "
                    "ambushed you, but Guwaru saved you.)")
        sm.setSpeakerID(DARKNELL)
        if sm.sendAskAccept("As if the ritual started, the Genesis Crux starts shuddering. Defeat Guard Captain "
                            "Darknell."):
            sm.startQuest(parentID)
            sm.setQRValue(parentID, "dir", "1")
    if sm.hasQuest(parentID) and sm.getChr().getInstance() is None:
        sm.warpInstanceIn(993070076, 0)
sm.dispose()

# [Limina] Final Operation (35848), from the quest notifier. Cygnus, who takes it back (3003992), stands nowhere:
# the scene is played here, then to the Giant's Heart, where Cygnus starts The Final Foe (35849).
CYGNUS = 3003992

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        if sm.sendAskAccept("It seems the final operation is ready. All that remains is your decision."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(There was unrest, confusion, and fear in everyone's heart. Empress Cygnus noticed this and raised "
                    "her voice to give strength to everyone.)")
        sm.setSpeakerID(CYGNUS)
        sm.sendNext("There is only one thing left now that our hesitation is gone. We will defeat the #rBlack Mage#k.")
        if sm.completeQuest(parentID):
            if sm.getChr().getInstance() is not None:
                sm.warpInstanceOut(450012500, 0)
            else:
                sm.warp(450012500, 0)
sm.dispose()

# Quest Map Exit (1064029) | Colossal Root quest map: same behaviour as the map's exit portal
THE_MYSTERIOUS_GIRL = 30002
sm.setSpeakerID(1064029)
if sm.hasQuest(THE_MYSTERIOUS_GIRL):
    sm.setPlayerAsSpeaker()
    sm.completeQuest(THE_MYSTERIOUS_GIRL)
    sm.sendNext("I should tell that girl about the exit first.")
elif sm.sendAskYesNo("Leave the Colossal Root and return to the Secret Swamp?"):
    sm.warp(105010200, 0) # Secret Swamp
sm.dispose()

# Lakelis (9020000) | Kerning City: First Time Together is run from the Cross World Party Quest waiting room
sm.setSpeakerID(9020000)
if sm.sendAskYesNo("#e<Party Quest: First Time Together>#n\r\n\r\nThis party quest now starts from my waiting room in #bSpiegelmann's Guest House#k. Shall I take you there?"):
    sm.warp(933010000, 0)
sm.dispose()

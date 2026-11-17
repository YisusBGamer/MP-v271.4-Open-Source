# 36327 The Truth Beneath the Hat - end (Cartalion, Pantheon).
sm.setSpeakerID(3000000)
sm.sendNext("You've come a long way from Victoria Island. What brings you to Pantheon?")
sm.setPlayerAsSpeaker()
sm.sendSay("#b(You tell Cartalion about Rondo and his ears.)#k")
sm.setSpeakerID(3000000)
if sm.completeQuest(parentID):
    sm.sendSayOkay("Fox ears, you say? Hmm... That reminds me of something. Talk to me again.")
sm.dispose()

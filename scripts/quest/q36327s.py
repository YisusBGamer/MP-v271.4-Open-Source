# 36327 The Truth Beneath the Hat - start (Rondo, Six Path Crossway). Rondo explains his fox ears; handed in to
# Cartalion in Pantheon.
sm.setSpeakerID(1541007)
sm.setPlayerAsSpeaker()
sm.sendNext("#b(What's so important about Rondo's hat?)#k")
sm.setSpeakerID(1541007)
sm.sendSay("...You saw them, didn't you? My ears. I'm not like other people. I've hidden them under this hat as long "
           "as I can remember.")
sm.sendSay("I don't know where I come from. But I heard there are people with ears like mine somewhere far away, near "
           "#bPantheon#k.")
if not sm.sendAskAccept("Will you help me find out? There's someone in #b#m400000000##k who might know: "
                        "#bCartalion#k."):
    sm.dispose()
sm.startQuest(parentID)
sm.chatScript("Ask Cartalion in Pantheon about fox-eared people.")
sm.dispose()

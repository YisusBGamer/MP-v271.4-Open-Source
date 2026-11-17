# [Vanishing Journey] The World Below the River (34174), Chief Legato
LEGATO = 3003111

sm.setSpeakerID(LEGATO)
sm.sendNext("Maple World? The Alliance? I'm sorry, I've never heard of such places.")
sm.sendNext("But... there are times when the reflection of a mysterious world can be seen below the river that flows through town.")
if sm.sendAskAccept("#bKima the Oarsman#k at the Lakeshore knows the river better than anyone. Ask him about it."):
    sm.startQuest(parentID)
sm.dispose()

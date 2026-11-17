# Forgotten Storage Chamber (610030200) next00: on to The Test of Agility (610030300), in the same party quest run,
# once all five sigils are lit (reactor/glpqskill0).
if sm.getFieldID() == 610030200 and sm.fieldHasProperty("Stage1Cleared"):
    sm.warp(610030300, 0, chr.getInstance() is not None)
else:
    sm.chatRed("Some of the Sigils have not been activated yet.")
sm.dispose()

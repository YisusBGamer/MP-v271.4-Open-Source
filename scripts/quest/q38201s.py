# 38201 [Karote] A New Mission - start (Neinheart, Ereve)
NEINHEART = 1101002

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("#h0#, welcome back. Word of your work in the desert has reached Ereve.")
sm.sendNext("Grandis and Maple World are drawing closer every day, and the High Flora are not idle. "
            "I would like you to hear what the Chief Knights have learned.")
if sm.sendAskAccept("Come and see me in Ereve when you are ready. Will you take on a new mission?"):
    sm.startQuest(parentID)
    sm.sendNext("Good. Talk to me again and we will begin.")
sm.dispose()

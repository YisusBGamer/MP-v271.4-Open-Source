# 38204 [Karote] A Gate to Distant Lands - start (Nicky, Hotel Arcus 2nd Floor)
NICKY = 3005248

sm.setSpeakerID(NICKY)
sm.setBoxChat()
sm.sendNext("Karote is a tower that keeps building itself. It grows higher and higher, then something knocks it down, "
            "and it starts all over again.")
sm.sendNext("My #bfather#k chased it his whole life. His last wish was to see it reach its peak. I've been following "
            "it ever since.")
if sm.sendAskAccept("It's growing again right now. I can guide you there, but it's a long way across the desert. "
                    "Maybe #b#p3005254##k could help us get there faster?"):
    sm.startQuest(parentID)
    sm.sendNext("Talk to #b#p3005254##k. I'll get ready.")
sm.dispose()

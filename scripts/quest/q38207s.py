# 38207 [Karote] A Hidden Mural - start (Nicky, Bottom Floor)
NICKY = 3005250

sm.setSpeakerID(NICKY)
sm.setBoxChat()
sm.sendNext("You're back! You just vanished, and then the whole tower started rebuilding itself!")
sm.sendNext("It's never risen this fast before. Never. Whatever you did in there, it woke something up.")
if sm.sendAskAccept("Everyone was worried about you. Go tell #b#p3005232##k what you saw."):
    sm.startQuest(parentID)
sm.dispose()

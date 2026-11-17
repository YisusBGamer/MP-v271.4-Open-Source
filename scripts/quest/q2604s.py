# Thief of the Shade (2604) - Ryden (1057001) in the Kerning City Back Alley, after Sugar's The Adventure Begins
RYDEN = 1057001

sm.setSpeakerID(RYDEN)
sm.sendNext("So you're the one Sugar brought to Victoria Island. You have a knack for staying unseen... I've been watching.")
sm.sendSay("I recommend you for a #bspecial mission#k. What it is, only #bLady Syl#k can tell you, IF she finds you worthy.")
if sm.sendAskAccept("When you accept, I'll send you to Lady Syl."):
    sm.startQuest(parentID)
    sm.warp(103050101, 0)
else:
    sm.sendNext("Come back when you've made up your mind.")
sm.dispose()

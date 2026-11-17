# 39510 Tired of Tranquility (Hoyoung, self-start). Done on reaching the Magic Training Grounds Entrance
# (enter_993160001 sets record 39510 move=1 and hands it in).
QUEST = 39510
ENTRANCE = 993160001

if not sm.hasQuest(QUEST) and not sm.hasQuestCompleted(QUEST):
    sm.setPlayerAsSpeaker()
    sm.sendNext("Just lyin' around doing nothing all day is getting boring, even for me. What'll happen if I'm so inactive I start growing moss, like a rock?")
    if sm.sendAskAccept("I kinda feel like going to the #r#m993160001##k for some reason. Guess there's a first time for everything."):
        sm.startQuest(QUEST)
if sm.hasQuest(QUEST) and sm.getFieldID() == ENTRANCE:
    sm.setQRValue(QUEST, "move", "1")
    sm.completeQuest(QUEST)
sm.dispose()

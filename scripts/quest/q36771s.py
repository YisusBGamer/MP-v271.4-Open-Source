# [Labyrinth of Suffering] Gap (36771), from the quest notifier: Azalin leads you to the Source of Suffering
# (450011660), where the giant core of Source of Suffering (36772) appears
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if not sm.hasQuest(parentID):
        if sm.sendAskAccept("Now you don't know if what you did for the people holds any meaning. It would be better to "
                            "do what Azalin says."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.sendNext("(Azalin led you deep into the Labyrinth. You were devastated that you might have killed the people "
                    "by your own hand...)")
        sm.sendNext("(...and that is when she revealed her true identity: Hilla!)")
        if sm.completeQuest(parentID):
            sm.warp(450011660, 0)
sm.dispose()

# 37613 [Reverse City] Onward and Onward Still - start: Barr (3004615); end: Barr (3004616) in #m450014160#
sm.setSpeakerID(3004615) # Barr
sm.sendNext("The override's half done. Let's move up the train while I work on it.")
if not sm.sendAskAccept("Meet me in #b#m450014160##k. Ready?"):
    sm.dispose()
sm.startQuest(parentID)

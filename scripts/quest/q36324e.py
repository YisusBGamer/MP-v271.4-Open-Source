# 36324 Mysterious Power - end (Athena Pierce). Finishing it opens every Explorer's 2nd job quest
# (1410/1414/1418/1421/1424/1427, Dual Blade 2638).
sm.setSpeakerID(1012100)
sm.sendNext("The Black Shadow, a voice in your head, and the Seal Stone restoring Maple Island... All of that happened?")
sm.sendSay("There's a mysterious power inside you. I can feel it. Nurture it, and one day you may be able to save everyone.")
if sm.completeQuest(parentID):
    sm.sendSayOkay("You're ready for the next step. Go see your #bjob instructor#k about your 2nd job advancement.")
sm.dispose()

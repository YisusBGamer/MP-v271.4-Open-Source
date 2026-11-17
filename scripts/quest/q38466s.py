# [Daily Quest] Save the Morass
import time

JEAN = 3003432
reqMob = 500

sm.setSpeakerID(JEAN)
sm.sendNext("Oh #h0#, I have been looking for you, I have something that needs your help...")
sm.setPlayerAsSpeaker()
sm.sendSay("What is it, Jean?")
sm.setSpeakerID(JEAN)
sm.sendSay("Please help us protect the #bMorass#k so that it does not become just a memory.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(35632): # [Moonbridge] Clear Path
        reqMob = 100
    elif sm.hasQuestCompleted(37871): # [Esfera] Mirrors in Mirrors
        reqMob = 300
    if sm.sendAskAccept("Hello! I need you to defeat " + str(reqMob) + " monsters around the #bMorass#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38467)
        elif reqMob == 300:
            sm.startQuest(38468)
        else:
            sm.startQuest(38469)
        sm.setSpeakerID(JEAN)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(JEAN)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(JEAN)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")

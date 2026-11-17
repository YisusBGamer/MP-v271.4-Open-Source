# [Daily Quest] Esfera Research Orders
import time

OLLIE = 3003530
reqMob = 500

sm.setSpeakerID(OLLIE)
sm.sendNext("There you are!")
sm.setPlayerAsSpeaker()
sm.sendSay("What's up, Ollie?")
sm.setSpeakerID(OLLIE)
sm.sendSay("We got orders from the Commanders. They want us to start #bresearching Esfera#k.")
sm.setPlayerAsSpeaker()
sm.sendSay("Research?")
sm.setSpeakerID(OLLIE)
sm.sendSay("Yes! This research is #rmonster hunting#k to look into the changes in the area.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(35731) or sm.hasQuestCompleted(36772): # [Labyrinth of Suffering] Source of Suffering
        reqMob = 100
    elif sm.hasQuestCompleted(35632): # [Moonbridge] Clear Path
        reqMob = 300
    if sm.sendAskAccept("Hello! I have an investigation order for today. Defeat " + str(reqMob) + " monsters around the #bEsfera#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38471)
        elif reqMob == 300:
            sm.startQuest(38472)
        else:
            sm.startQuest(38473)
        sm.setSpeakerID(OLLIE)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(OLLIE)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(OLLIE)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")

# [Daily Quest] Limina Research
import time

VELIVAH = 3003810
reqMob = 100

sm.setSpeakerID(VELIVAH)
sm.sendNext("There you are!")
sm.setPlayerAsSpeaker()
sm.sendSay("What's up, Velivah?")
sm.setSpeakerID(VELIVAH)
sm.sendSay("We received a request from the Guardians of Time. They want us to start #bresearching Limina#k.")
sm.setPlayerAsSpeaker()
sm.sendSay("Research?")
sm.setSpeakerID(VELIVAH)
sm.sendSay("Yes! This research is #rmonster hunting#k to look into the changes in the area.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.sendAskAccept("Hello! I have an investigation request today. Defeat " + str(reqMob) + " monsters around the #bLimina#k area."):
        sm.startQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        sm.setSpeakerID(VELIVAH)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(VELIVAH)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(VELIVAH)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")

# [Daily Quest] Clean Up Around Hotel Arcus
import time

ARCHELON = 3005016
reqMob = 500

sm.setSpeakerID(ARCHELON)
if sm.hasQuestCompleted(38404):  # Shangri-La reached
    reqMob = 100
elif sm.hasQuestCompleted(38319):  # Odium reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bHotel Arcus#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19025)
    elif reqMob == 300:
        sm.startQuest(19026)
    else:
        sm.startQuest(19027)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")

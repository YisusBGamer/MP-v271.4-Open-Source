# [Daily Quest] Shangri-La Contamination Purification
import time

TAI_YU = 3006163
reqMob = 500

sm.setSpeakerID(TAI_YU)
if sm.hasQuestCompleted(38702):  # Carcion reached
    reqMob = 100
elif sm.hasQuestCompleted(38653):  # Arteria reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bShangri-La#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19033)
    elif reqMob == 300:
        sm.startQuest(19034)
    else:
        sm.startQuest(19035)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
